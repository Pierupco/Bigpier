package com.pier.application;

import com.pier.application.model.RoleEntity;
import com.pier.application.model.User;
import com.pier.application.model.security.AccessInfo;
import com.pier.application.model.security.UserAuthEntity;
import com.pier.application.processor.AuthRoleEntityProcessor;
import com.pier.application.service.security.token.AuthToken;
import com.pier.application.service.security.token.TokenService;
import com.pier.application.service.security.token.SingleOfflineTokenService;
import com.pier.application.service.security.token.exception.UnrecognizedDeviceException;
import com.pier.application.service.security.AuthRoleEntityService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestAuthRoleEntityProcessor {

    private static AuthRoleEntityProcessor processor;

    @BeforeClass
    public static void setUp() {
        final TokenService service = new SingleOfflineTokenService();
        processor = new AuthRoleEntityProcessor(service);
    }

    @Test
    public void testRegisterNewRole() {
        final Long id = 12L;
        final String phone = "12345";
        final String password = "123456";
        final String platformStr = "API";
        final String deviceToken = "devicetoken";
        final Long ip = 0l;
        final String countryCode = "US";
        final User user = new User();
        user.setId(id);
        final UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setId(id);
        final AccessInfo accessInfo = new AccessInfo(ip, platformStr, deviceToken);
        //Mocks
        final AuthRoleEntityService mockService = mock(AuthRoleEntityService.class);
        when(mockService.getRoleByPhone(anyString())).thenReturn(null, user);
        when(mockService.newRoleInstance()).thenReturn(user);
        when(mockService.newAuthEntityInstance(anyLong())).thenReturn(userAuthEntity);
        when(mockService.saveRole((RoleEntity) any())).thenReturn(id);

        final AuthToken token = processor.register(mockService, phone, password, countryCode, accessInfo);

        Assert.assertNotNull(token);
        Assert.assertEquals(token.getUserId(), id);
        Assert.assertNotNull(token.getValue());
    }

    @Test
    public void testRegisterExistedPhone() {
        final Long id = 12L;
        final String phone = "12345";
        final String password = "12345";
        final String platformStr = "API";
        final String deviceToken = "devicetoken";
        final Long ip = 0l;
        final String countryCode = "US";
        final User user = new User();
        user.setId(id);
        final AccessInfo accessInfo = new AccessInfo(ip, platformStr, deviceToken);
        //Mocks
        final AuthRoleEntityService mockService = mock(AuthRoleEntityService.class);
        when(mockService.getRoleByPhone(anyString())).thenReturn(user);

        final AuthToken token = processor.register(mockService, phone, password, countryCode, accessInfo);

        Assert.assertNull(token);
    }

    @Test(expected = UnrecognizedDeviceException.class)
    public void testRegisterAndLoginThrowException() throws Exception {
        final Long id = 12L;
        final String phone = "12345";
        final String password = "12345";
        final String platformStr = "API";
        final String deviceToken = "devicetoken";
        final String deviceToken2 = "devicetoken2";
        final Long ip = 0l;
        final String countryCode = "US";
        final User user = new User();
        user.setId(id);
        final UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setId(id);
        userAuthEntity.setPassword(password);
        final AccessInfo accessInfo = new AccessInfo(ip, platformStr, deviceToken);
        final AccessInfo accessInfo2 = new AccessInfo(ip, platformStr, deviceToken2);
        //Mocks
        final AuthRoleEntityService mockService = mock(AuthRoleEntityService.class);
        when(mockService.getRoleByPhone(anyString())).thenReturn(null, user);
        when(mockService.newRoleInstance()).thenReturn(user);
        when(mockService.newAuthEntityInstance(anyLong())).thenReturn(userAuthEntity);
        when(mockService.saveRole((RoleEntity) any())).thenReturn(id);
        when(mockService.getAuthEntityById(anyLong())).thenReturn(userAuthEntity);

        final AuthToken token = processor.register(mockService, phone, password, countryCode, accessInfo);

        Assert.assertNotNull(token);

        Assert.assertEquals(token.getUserId(), id);
        Assert.assertNotNull(token.getValue());

        processor.login(mockService, phone, password, accessInfo2);
    }

    @Test
    public void testRegisterAndLoginSuccessfully() throws Exception {
        final Long id = 12L;
        final String phone = "12345";
        final String password = "12345";
        final String platformStr = "API";
        final String deviceToken = "devicetoken";
        final Long ip = 0l;
        final String countryCode = "US";
        final User user = new User();
        user.setId(id);
        final UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setId(id);
        userAuthEntity.setPassword(password);
        final AccessInfo accessInfo = new AccessInfo(ip, platformStr, deviceToken);
        //Mocks
        final AuthRoleEntityService mockService = mock(AuthRoleEntityService.class);
        when(mockService.getRoleByPhone(anyString())).thenReturn(null, user);
        when(mockService.newRoleInstance()).thenReturn(user);
        when(mockService.newAuthEntityInstance(anyLong())).thenReturn(userAuthEntity);
        when(mockService.saveRole((RoleEntity) any())).thenReturn(id);
        when(mockService.getAuthEntityById(anyLong())).thenReturn(userAuthEntity);

        final AuthToken token = processor.register(mockService, phone, password, countryCode, accessInfo);

        Assert.assertNotNull(token);

        Assert.assertEquals(token.getUserId(), id);
        Assert.assertNotNull(token.getValue());

        final AuthToken token2 = processor.login(mockService, phone, password, accessInfo);

        Assert.assertNotNull(token2);
        Assert.assertNotNull(token2.getValue());
        Assert.assertEquals(token2.getUserId(), id);
    }

    @Test
    public void testRegisterAndLoginAndUpdateSuccessfully() throws Exception {
        final Long id = 12L;
        final String phone = "12345";
        final String password = "12345";
        final String platformStr = "API";
        final String deviceToken = "devicetoken";
        final String email = "test@pier.com";
        final Long ip = 0l;
        final String countryCode = "US";
        final User user = new User();
        user.setEmail(email);
        user.setId(id);
        final UserAuthEntity userAuthEntity = new UserAuthEntity();
        userAuthEntity.setId(id);
        userAuthEntity.setPassword(password);
        final AccessInfo accessInfo = new AccessInfo(ip, platformStr, deviceToken);

        final User user2 = new User();
        user2.setEmail(email);
        user2.setId(id);
        user2.setAddress("US");
        //Mocks
        final AuthRoleEntityService mockService = mock(AuthRoleEntityService.class);
        when(mockService.getRoleByPhone(anyString())).thenReturn(null, user);
        when(mockService.newRoleInstance()).thenReturn(user);
        when(mockService.saveRole((RoleEntity) any())).thenReturn(id);
        when(mockService.newAuthEntityInstance(anyLong())).thenReturn(userAuthEntity);
        when(mockService.saveRole((RoleEntity) any())).thenReturn(id);
        when(mockService.getAuthEntityById(anyLong())).thenReturn(userAuthEntity);
        when(mockService.getRoleById(anyLong())).thenReturn(user, user2);

        final AuthToken token = processor.register(mockService, phone, password, countryCode, accessInfo);

        Assert.assertNotNull(token);

        Assert.assertEquals(token.getUserId(), id);
        Assert.assertNotNull(token.getValue());

        final AuthToken token2 = processor.login(mockService, phone, password, accessInfo);

        Assert.assertNotNull(token2);
        Assert.assertNotNull(token2.getValue());
        Assert.assertEquals(token2.getUserId(), id);

        final RoleEntity newEntity =
                processor.updateRoleEntityInfo(mockService, user2, token2.getValue(), accessInfo);

        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getId(), id);
        Assert.assertEquals(newEntity.getAddress(), "US");
    }

}
