package com.pier.application.service.security.token;

import com.pier.application.model.enums.AccessPlatform;
import com.pier.application.model.security.AuthEntity;
import com.pier.application.security.AESEncryptionUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class SingleOfflineTokenService implements TokenService {

    private final Logger logger = Logger.getLogger(SingleOfflineTokenService.class);
    private static final String SALT_KEY_BASE = "(!(sDK*O`x.>j<YC.3T?>sEfV&8r`UboK(tHj(abUeA><|f^";

    @Nullable
    public AuthToken getToken(@Nonnull final AuthEntity entity,
                              @Nonnull final AccessPlatform currentPlatform) {
        return getToken(entity, currentPlatform, SALT_KEY_BASE);
    }

    @Nullable
    public AuthToken getToken(@Nonnull final AuthEntity entity,
                              @Nonnull final AccessPlatform currentPlatform,
                              @Nonnull final String saltKeyBase) {
        final String registeredDeviceToken = entity.getDeviceToken();

        final long createdTime = entity.getCreatedOn().getTime();
        //currently we won't use this but let's include it now;
        final long expirationTime = Long.MAX_VALUE;
        final String email = entity.getEmail();
        final long id = entity.getId();
        return generate(createdTime, expirationTime, currentPlatform, registeredDeviceToken, id, email, saltKeyBase);
    }

    public boolean isValidTokenString(@Nonnull final AuthEntity entity,
                                      @Nonnull final String token,
                                      @Nonnull final AccessPlatform currentPlatform) {
        return isValidTokenString(entity, token, currentPlatform, SALT_KEY_BASE);
    }

    public boolean isValidTokenString(@Nonnull final AuthEntity entity,
                                      @Nonnull final String authToken,
                                      @Nonnull final AccessPlatform currentPlatform,
                                      @Nonnull final String saltKeyBase) {
        try {
            final String registeredDeviceToken = entity.getDeviceToken();
            final long createdTime = entity.getCreatedOn().getTime();
            final String saltKey = getSaltKey(createdTime, saltKeyBase);
            final String rawToken;
            rawToken = AESEncryptionUtil.decrypt(authToken, saltKey.getBytes("UTF-8"));
            if (rawToken == null) {
                return false;
            }
            final String[] components = rawToken.split(":");
            if (components.length != 6) {
                return false;
            }
            final long expirationTime = Long.parseLong(components[4]);
            if (expirationTime < System.currentTimeMillis()) {
                return false;
            }
            return currentPlatform.name().equals(components[0])
                    && registeredDeviceToken.equals(components[1])
                    && Long.valueOf(components[2]).equals(entity.getId())
                    && components[3].equals(entity.getEmail());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return false;
        }

    }

    public void revokeToken(@Nonnull final AuthEntity entity) {
        entity.revokeToken();
    }

    @Nonnull
    private AuthToken generate(final long createdTime,
                               final long expirationTime,
                               @Nonnull final AccessPlatform platform,
                               @Nonnull final String deviceToken,
                               final long id,
                               final String email,
                               final String saltKeyBase) {
        final String saltKey = getSaltKey(createdTime, saltKeyBase);
        final long now = new Date().getTime();
        final String userRawToken = platform.name() + ":" + deviceToken + ":" + id + ":" + email + ":" + expirationTime + ":" + now;
        final String userTokenString;
        try {
            userTokenString = AESEncryptionUtil.encrypt(userRawToken, saltKey.getBytes("UTF-8"));
            return new AuthToken(userTokenString, new Date(Long.MAX_VALUE), id);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private String getSaltKey(final long createdTime, final String saltKeyBase) {
        return (createdTime + saltKeyBase).substring(0, 16);
    }
}
