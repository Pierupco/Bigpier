package com.pier.application.service.security;

import com.pier.application.model.security.AuthEntity;

public interface AuthEntityService {

    public AuthEntity getAuthEntityById(final Long id);

    public void deleteAuthEntityById(final Long id);

    public void updateAuthEntity(final AuthEntity authEntity);

    public AuthEntity newAuthEntityInstance(final Long id);
}
