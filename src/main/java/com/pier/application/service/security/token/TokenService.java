package com.pier.application.service.security.token;

import com.pier.application.model.security.AuthEntity;
import com.pier.application.model.enums.AccessPlatform;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface TokenService {

    @Nullable
    public AuthToken getToken(@Nonnull final AuthEntity entity,
                              @Nonnull final AccessPlatform currentPlatform);

    public AuthToken getToken(@Nonnull final AuthEntity entity,
                              @Nonnull final AccessPlatform currentPlatform,
                              @Nonnull final String saltKeyBase);

    public boolean isValidTokenString(@Nonnull final AuthEntity entity,
                                      @Nonnull final String tokenString,
                                      @Nonnull final AccessPlatform currentPlatform);

    public boolean isValidTokenString(@Nonnull final AuthEntity entity,
                                      @Nonnull final String tokenString,
                                      @Nonnull final AccessPlatform currentPlatform,
                                      @Nonnull final String saltKeyBase);

    public void revokeToken(@Nonnull final AuthEntity entity);

}
