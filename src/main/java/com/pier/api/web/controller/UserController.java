package com.pier.api.web.controller;

import com.pier.api.web.response.Response;
import com.pier.api.web.response.ResponseError;
import com.pier.application.processor.AuthRoleEntityProcessor;
import com.pier.application.model.User;
import com.pier.application.model.security.AccessInfo;
import com.pier.application.service.security.AuthUserService;
import com.pier.application.service.security.token.AuthToken;
import com.pier.application.service.security.token.exception.UnrecognizedDeviceException;
import com.pier.support.util.JsonSerDe;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/user")
public class UserController {
    private static final Log log = LogFactory.getLog(UserController.class);

    private final JsonSerDe jsonSerDe = new JsonSerDe();

    private final AuthUserService authUserService;
    private final AuthRoleEntityProcessor authRoleEntityProcessor;

    @Autowired
    public UserController(AuthUserService authUserService, AuthRoleEntityProcessor authRoleEntityProcessor) {
        this.authUserService = authUserService;
        this.authRoleEntityProcessor = authRoleEntityProcessor;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = {"unchecked"})
    public Response doLoginApi(@RequestParam("phoneOrEmail") String phoneOrEmail,
                               @RequestParam("password") String password,
                               @RequestParam("platform") String platformStr,
                               @RequestParam("deviceToken") String deviceToken,
                               @RequestParam("ip") Long ip) {
        try {
            final AuthToken authToken = authRoleEntityProcessor.login(authUserService, phoneOrEmail, password, new AccessInfo(ip, platformStr, deviceToken));

            if (authToken == null) {
                return Response.FAILED_RESPONSE;
            } else {
                return new Response<AuthToken>(authToken);
            }
        } catch (UnrecognizedDeviceException e) {
            //TODO: Front end sends verification note
            return Response.getErrorResponse(ResponseError.UNRECOGNIZED_DEVICE);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = {"unchecked"})
    public Response registerApi(@RequestParam("phone") String phone,
                                @RequestParam("password") String password,
                                @RequestParam("country") String countryCode,
                                @RequestParam("platform") String platformStr,
                                @RequestParam("deviceToken") String deviceToken,
                                @RequestParam("ip") Long ip) {
        final AuthToken authToken = authRoleEntityProcessor.register(authUserService, phone, password, countryCode, new AccessInfo(ip, platformStr, deviceToken));
        if (authToken == null) {
            return Response.FAILED_RESPONSE;
        } else {
            return new Response<AuthToken>(authToken);
        }
    }

    @RequestMapping(value = "/registerNewDevice", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = {"unchecked"})
    public Response registerNewDeviceApi(@RequestParam("phone") String phone,
                                @RequestParam("password") String password,
                                @RequestParam("verificationCode") String verificationCode,
                                @RequestParam("platform") String platformStr,
                                @RequestParam("deviceToken") String deviceToken,
                                @RequestParam("ip") Long ip) {
        final AuthToken authToken = authRoleEntityProcessor.registerNewDevice(authUserService, phone, password, verificationCode, new AccessInfo(ip, platformStr, deviceToken));
        if (authToken == null) {
            return Response.FAILED_RESPONSE;
        } else {
            return new Response<AuthToken>(authToken);
        }
    }

    @RequestMapping(value = "/{userId}/info", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = {"unchecked"})
    public Response getUserInfoApi(@PathVariable("userId") Long userId,
                                   @RequestParam("token") String tokenString,
                                   @RequestParam("platform") String platformStr,
                                   @RequestParam("deviceToken") String deviceToken,
                                   @RequestParam("ip") Long ip) {
        try {
            final User user = (User) authRoleEntityProcessor.verifyAndGetRoleEntity(authUserService, userId, tokenString, new AccessInfo(ip, platformStr, deviceToken));

            if (user == null) {
                return Response.FAILED_RESPONSE;
            } else {
                return new Response<User>(user);
            }
        } catch (UnrecognizedDeviceException e) {
            //TODO: Front end sends verification note
            return Response.getErrorResponse(ResponseError.UNRECOGNIZED_DEVICE);
        }
    }

    @RequestMapping(value = "/{userId}/update", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = {"unchecked"})
    public Response updateUserInfoApi(@PathVariable("userId") Long userId,
                                      @RequestParam("user") String userJson,
                                      @RequestParam("token") String tokenString,
                                      @RequestParam("platform") String platformStr,
                                      @RequestParam("deviceToken") String deviceToken,
                                      @RequestParam("ip") Long ip) {
        try {
            final User user = (User) authRoleEntityProcessor.verifyAndGetRoleEntity(authUserService, userId, tokenString, new AccessInfo(ip, platformStr, deviceToken));

            if (user == null) {
                log.error("Can't find user id " + userId);
                return Response.FAILED_RESPONSE;
            } else {
                try {
                    final User newUser = jsonSerDe.deserialize(userJson, User.class);
                    if (newUser == null) {
                        log.error("Fail to deserialize the user: " + userJson);
                        return Response.FAILED_RESPONSE;
                    }
                    authRoleEntityProcessor.updateRoleEntityInfo(authUserService, newUser, tokenString, new AccessInfo(ip, platformStr, deviceToken));
                    return new Response<User>(newUser);
                } catch (Exception e) {
                    log.error("Fail to update the user for new info : " + userJson);
                    return Response.FAILED_RESPONSE;
                }
            }
        } catch (UnrecognizedDeviceException e) {
            //TODO: Front end sends verification note
            return Response.getErrorResponse(ResponseError.UNRECOGNIZED_DEVICE);
        }
    }

    @RequestMapping(value = "/{userId}/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = {"unchecked"})
    public Response updateUserPassword(@PathVariable("userId") Long userId,
                                       @RequestParam("oldPassword") String oldPassword,
                                       @RequestParam("newPassword") String newPassword,
                                       @RequestParam("token") String tokenString,
                                       @RequestParam("platform") String platformStr,
                                       @RequestParam("deviceToken") String deviceToken,
                                       @RequestParam("ip") Long ip) {
        try {
            final AuthToken authToken = authRoleEntityProcessor.updatePassword(authUserService,
                    userId,
                    oldPassword,
                    newPassword,
                    tokenString,
                    new AccessInfo(ip, platformStr, deviceToken));

            if (authToken == null) {
                log.error("Can't update password for " + userId);
                return Response.FAILED_RESPONSE;
            } else {
                return new Response<AuthToken>(authToken);
            }
        } catch (UnrecognizedDeviceException e) {
            //TODO: Front end sends verification note
            return Response.getErrorResponse(ResponseError.UNRECOGNIZED_DEVICE);
        }
    }

    @RequestMapping(value = "/{userId}/logout", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = {"unchecked"})
    public Response doLogoutApi(@PathVariable("userId") Long userId,
                                @RequestParam("token") String tokenString,
                                @RequestParam("platform") String platformStr,
                                @RequestParam("deviceToken") String deviceToken,
                                @RequestParam("ip") Long ip) {
        try {
            final boolean success = authRoleEntityProcessor.logout(authUserService, userId, tokenString, new AccessInfo(ip, platformStr, deviceToken));
            if (!success) {
                return Response.FAILED_RESPONSE;
            } else {
                return new Response<AuthToken>(null);
            }
        } catch (UnrecognizedDeviceException e) {
            //TODO: Front end sends verification note
            return Response.getErrorResponse(ResponseError.UNRECOGNIZED_DEVICE);
        }
    }
}