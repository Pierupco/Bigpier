package com.pier.application.processor;

import com.google.common.annotations.VisibleForTesting;
import com.pier.application.model.RoleEntity;
import com.pier.application.model.enums.AccessPlatform;
import com.pier.application.model.security.AccessInfo;
import com.pier.application.model.security.AuthEntity;
import com.pier.application.service.security.token.AuthToken;
import com.pier.application.service.security.token.TokenService;
import com.pier.application.service.security.token.exception.UnrecognizedDeviceException;
import com.pier.application.service.security.AuthRoleEntityService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;

//TODO: Bad naming
@Component
public class AuthRoleEntityProcessor {
    private static final Log log = LogFactory.getLog(AuthRoleEntityProcessor.class);

    private final TokenService authService;

    @VisibleForTesting
    @Autowired
    public AuthRoleEntityProcessor(TokenService authService) {
        this.authService = authService;
    }

    @Nullable
    public AuthToken login(@Nonnull final AuthRoleEntityService authRoleEntityService,
                           @Nonnull final String roleEmailOrPhone,
                           @Nonnull final String password,
                           @Nonnull final AccessInfo accessInfo) throws UnrecognizedDeviceException {
        final RoleEntity roleEntity = this.getRoleByEmailOrPhone(authRoleEntityService, roleEmailOrPhone);
        if (roleEntity == null) {
            return null;
        }
        // this will throw UnrecognizedDeviceException, don't catch it;
        final AuthEntity authEntity = verifyRoleEntityByPassword(authRoleEntityService, roleEntity.getId(), password, accessInfo);
        if (authEntity == null) {
            return null;
        }
        return authService.getToken(authEntity, accessInfo.getPlatform());
    }

    @Nullable
    public AuthToken register(@Nonnull final AuthRoleEntityService authRoleEntityService,
                              @Nonnull final String phone,
                              @Nonnull final String password,
                              @Nonnull final String countryCode,
                              @Nonnull final AccessInfo accessInfo) {
        final RoleEntity role = authRoleEntityService.getRoleByPhone(phone);
        if (role == null) {
            try {
                final RoleEntity newRole = authRoleEntityService.newRoleInstance();
                newRole.setPhone(phone);
                newRole.setCountryCode(countryCode);
                final Long userId = authRoleEntityService.saveRole(newRole);
                if (userId == null) {
                    return null;
                }
                final AuthEntity newAuthEntity = authRoleEntityService.newAuthEntityInstance(userId);
                final AccessPlatform platform = accessInfo.getPlatform();

                newAuthEntity.setCreatedOn(new Date());
                newAuthEntity.setCreatedBy(platform);
                newAuthEntity.updatePassword(password);
                newAuthEntity.setDeviceToken(accessInfo.getDeviceToken());
                updateRoleEntityAccessInfo(authRoleEntityService, newAuthEntity, accessInfo, true);

                return authService.getToken(newAuthEntity, platform);
            } catch (Exception e) {
                log.error("Role registration failed", e);
            }
        }
        return null;
    }

    @Nullable
    public AuthToken registerNewDevice(@Nonnull final AuthRoleEntityService authRoleEntityService,
                                       @Nonnull final String phone,
                                       @Nonnull final String password,
                                       @Nonnull final String verificationCode,
                                       @Nonnull final AccessInfo accessInfo) {
        final RoleEntity roleEntity = authRoleEntityService.getRoleByPhone(phone);
        if (roleEntity == null) {
            return null;
        }
        final AuthEntity authEntity = authRoleEntityService.getAuthEntityById(roleEntity.getId());
        if (authEntity.isValidPassword(password) && verificationCode.equals(authEntity.getVerificationCode())) {
            authEntity.setDeviceToken(accessInfo.getDeviceToken());
            updateRoleEntityAccessInfo(authRoleEntityService, authEntity, accessInfo, false);
            return authService.getToken(authEntity, accessInfo.getPlatform());
        } else {
            return null;
        }
    }

    @Nullable
    public AuthToken updatePassword(@Nonnull final AuthRoleEntityService authRoleEntityService,
                                    @Nonnull final Long roleId,
                                    @Nonnull final String oldPassword,
                                    @Nonnull final String newPassword,
                                    @Nonnull final String tokenString,
                                    @Nonnull final AccessInfo accessInfo) throws UnrecognizedDeviceException {
        final AuthEntity authEntity = authRoleEntityService.getAuthEntityById(roleId);
        if (authEntity == null) {
            return null;
        }
        try {
            if (authService.isValidTokenString(authEntity, tokenString, accessInfo.getPlatform())
                    && authEntity.isValidPassword(oldPassword)) {
                authEntity.updatePassword(newPassword);
                updateRoleEntityAccessInfo(authRoleEntityService, authEntity, accessInfo, true);
                return authService.getToken(authEntity, accessInfo.getPlatform());
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }


    @Nullable
    public RoleEntity updateRoleEntityInfo(@Nonnull final AuthRoleEntityService authRoleEntityService,
                                           @Nonnull final RoleEntity newRoleEntity,
                                           @Nonnull final String tokenString,
                                           @Nonnull final AccessInfo accessInfo) throws UnrecognizedDeviceException {
        final Long roleId = newRoleEntity.getId();
        final RoleEntity roleEntity = verifyAndGetRoleEntity(authRoleEntityService, roleId, tokenString, accessInfo);
        if (roleEntity != null) {
            try {
                authRoleEntityService.saveRole(newRoleEntity);
                final AuthEntity authEntity = authRoleEntityService.getAuthEntityById(roleId);
                updateRoleEntityAccessInfo(authRoleEntityService, authEntity, accessInfo, true);
                return authRoleEntityService.getRoleById(roleId);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Nullable
    public boolean logout(@Nonnull final AuthRoleEntityService roleEntityService,
                          @Nonnull final Long roleId,
                          @Nonnull final String tokenString,
                          @Nonnull final AccessInfo accessInfo) throws UnrecognizedDeviceException {
        return revokeToken(roleEntityService, roleId, tokenString, accessInfo);
    }

    @Nullable
    private RoleEntity getRoleByEmailOrPhone(@Nonnull AuthRoleEntityService authRoleEntityService,
                                             @Nonnull final String roleEmailOrPhone) {
        return roleEmailOrPhone.contains("@") ?
                authRoleEntityService.getRoleByEmail(roleEmailOrPhone) : authRoleEntityService.getRoleByPhone(roleEmailOrPhone);
    }

    private boolean revokeToken(@Nonnull AuthRoleEntityService authRoleEntityService,
                                @Nonnull final Long roleId,
                                @Nonnull final String tokenString,
                                @Nonnull final AccessInfo accessInfo) throws UnrecognizedDeviceException {
        final AuthEntity authEntity = verifyRoleEntityBySingleOfflineToken(authRoleEntityService, roleId, tokenString, accessInfo);
        if (authEntity == null) {
            return false;
        }
        authEntity.revokeToken();
        authRoleEntityService.updateAuthEntity(authEntity);
        return true;
    }

    private void updateRoleEntityAccessInfo(@Nonnull AuthRoleEntityService authRoleEntityService,
                                            @Nonnull final AuthEntity authEntity,
                                            @Nonnull final AccessInfo accessInfo,
                                            final boolean modified) {
        final Date now = new Date();
        if (modified) {
            authEntity.setModifiedOn(now);
            authEntity.setModifiedBy(accessInfo.getPlatform());
        }
        authEntity.setAccessedOn(now);
        authEntity.setAccessedBy(accessInfo.getPlatform());
        authEntity.setAccessIp(accessInfo.getIp());
        authRoleEntityService.updateAuthEntity(authEntity);
    }

    @VisibleForTesting
    @Nullable
    public RoleEntity verifyAndGetRoleEntity(@Nonnull final AuthRoleEntityService authRoleEntityService,
                                             @Nonnull final Long roleId,
                                             @Nonnull final String tokenString,
                                             @Nonnull final AccessInfo accessInfo) throws UnrecognizedDeviceException {
        if (StringUtils.isEmpty(tokenString)) {
            return null;
        }
        final AuthEntity authEntity = verifyRoleEntityBySingleOfflineToken(authRoleEntityService, roleId, tokenString, accessInfo);
        if (authEntity == null) {
            return null;
        } else {
            return authRoleEntityService.getRoleById(roleId);
        }
    }

    @VisibleForTesting
    @Nullable
    public AuthEntity verifyRoleEntityByPassword(@Nonnull final AuthRoleEntityService authRoleEntityService,
                                                 @Nonnull final Long roleId,
                                                 @Nonnull final String password,
                                                 @Nonnull final AccessInfo accessInfo) throws UnrecognizedDeviceException {
        final AuthEntity authEntity = authRoleEntityService.getAuthEntityById(roleId);
        if (authEntity.isValidPassword(password)) {
            updateRoleEntityAccessInfo(authRoleEntityService, authEntity, accessInfo, false);
            if (accessInfo.getDeviceToken().equals(authEntity.getDeviceToken())) {
                return authEntity;
            } else {
                throw new UnrecognizedDeviceException(String.format("Unrecognized device token %s for user %d", accessInfo.getDeviceToken(), roleId));
            }
        }
        return null;
    }

    @VisibleForTesting
    @Nullable
    public AuthEntity verifyRoleEntityBySingleOfflineToken(@Nonnull final AuthRoleEntityService authRoleEntityService,
                                                           @Nonnull final Long roleId,
                                                           @Nonnull final String tokenString,
                                                           @Nonnull final AccessInfo accessInfo) throws UnrecognizedDeviceException {
        final AuthEntity authEntity = authRoleEntityService.getAuthEntityById(roleId);
        if (authService.isValidTokenString(authEntity, tokenString, accessInfo.getPlatform())) {
            updateRoleEntityAccessInfo(authRoleEntityService, authEntity, accessInfo, false);
            if (accessInfo.getDeviceToken().equals(authEntity.getDeviceToken())) {
                return authEntity;
            } else {
                throw new UnrecognizedDeviceException(String.format("Unrecognized device token %s for user %d", accessInfo.getDeviceToken(), roleId));
            }
        }
        return null;
    }
}
