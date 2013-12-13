package com.pier.application.security;

import com.pier.application.model.enums.AccessPlatform;
import com.pier.application.model.security.UserAuthEntity;
import com.pier.application.service.security.token.AuthToken;
import com.pier.application.service.security.token.SingleOfflineTokenService;
import com.pier.application.service.security.token.exception.UnrecognizedDeviceException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class TestSingleOfflineTokenAuthenticationService {

    @Test
    public void testGetToken() throws UnrecognizedDeviceException {
        final SingleOfflineTokenService
                service = new SingleOfflineTokenService();
        final UserAuthEntity testEntity = new UserAuthEntity();
        final String currentDeviceToken = "FE66489F304DC75B8D6E8200DFF8A456E8DAEACEC428B427E9518741C92C6660";
        final AccessPlatform platform = AccessPlatform.API;
        testEntity.setCreatedBy(platform);
        testEntity.setCreatedOn(new Date());
        testEntity.setId(1234L);
        testEntity.setDeviceToken(currentDeviceToken);
        final AuthToken authToken = service.getToken(testEntity, platform);
        Assert.assertNotNull(authToken);
        final boolean isValid = service.isValidTokenString(testEntity, authToken.getValue(), platform);
        Assert.assertTrue(isValid);
    }
}
