package com.pier.api.web.controller;

import com.pier.api.web.response.Response;
import com.pier.api.web.response.ResponseError;
import com.pier.application.processor.AuthRoleEntityProcessor;
import com.pier.application.model.Merchant;
import com.pier.application.model.security.AccessInfo;
import com.pier.application.service.security.AuthMerchantService;
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
@RequestMapping("/merchant")
public class MerchantController {
    private static final Log log = LogFactory.getLog(MerchantController.class);

    private final JsonSerDe jsonSerDe = new JsonSerDe();

    private final AuthMerchantService authMerchantService;
    private final AuthRoleEntityProcessor authRoleEntityProcessor;

    @Autowired
    public MerchantController(AuthMerchantService authMerchantService, AuthRoleEntityProcessor authRoleEntityProcessor) {
        this.authMerchantService = authMerchantService;
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
            final AuthToken authToken = authRoleEntityProcessor.login(authMerchantService, phoneOrEmail, password, new AccessInfo(ip, platformStr, deviceToken));

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
        final AuthToken authToken = authRoleEntityProcessor.register(authMerchantService, phone, password, countryCode, new AccessInfo(ip, platformStr, deviceToken));
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
        final AuthToken authToken = authRoleEntityProcessor.registerNewDevice(authMerchantService, phone, password, verificationCode, new AccessInfo(ip, platformStr, deviceToken));
        if (authToken == null) {
            return Response.FAILED_RESPONSE;
        } else {
            return new Response<AuthToken>(authToken);
        }
    }

    @RequestMapping(value = "/{merchantId}/info", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = {"unchecked"})
    public Response getMerchantInfoApi(@PathVariable("merchantId") Long merchantId,
                                       @RequestParam("token") String tokenString,
                                       @RequestParam("platform") String platformStr,
                                       @RequestParam("deviceToken") String deviceToken,
                                       @RequestParam("ip") Long ip) {
        try {
            final Merchant merchant = (Merchant) authRoleEntityProcessor.verifyAndGetRoleEntity(authMerchantService, merchantId, tokenString, new AccessInfo(ip, platformStr, deviceToken));

            if (merchant == null) {
                return Response.FAILED_RESPONSE;
            } else {
                return new Response<Merchant>(merchant);
            }
        } catch (UnrecognizedDeviceException e) {
            //TODO: Front end sends verification note
            return Response.getErrorResponse(ResponseError.UNRECOGNIZED_DEVICE);
        }
    }

    @RequestMapping(value = "/{merchantId}/update", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = {"unchecked"})
    public Response updateMerchantInfoApi(@PathVariable("merchantId") Long merchantId,
                                          @RequestParam("merchant") String merchantJson,
                                          @RequestParam("token") String tokenString,
                                          @RequestParam("platform") String platformStr,
                                          @RequestParam("deviceToken") String deviceToken,
                                          @RequestParam("ip") Long ip) {
        try {
            final Merchant merchant = (Merchant) authRoleEntityProcessor.verifyAndGetRoleEntity(authMerchantService, merchantId, tokenString, new AccessInfo(ip, platformStr, deviceToken));

            if (merchant == null) {
                log.error("Can't find merchant id " + merchantId);
                return Response.FAILED_RESPONSE;
            } else {
                try {
                    final Merchant newMerchant = jsonSerDe.deserialize(merchantJson, Merchant.class);
                    if (newMerchant == null) {
                        log.error("Fail to deserialize the merchant: " + merchantJson);
                        return Response.FAILED_RESPONSE;
                    }
                    authRoleEntityProcessor.updateRoleEntityInfo(authMerchantService, newMerchant, tokenString, new AccessInfo(ip, platformStr, deviceToken));
                    return new Response<Merchant>(newMerchant);
                } catch (Exception e) {
                    log.error("Fail to update the merchant for new info : " + merchantJson);
                    return Response.FAILED_RESPONSE;
                }
            }
        } catch (UnrecognizedDeviceException e) {
            //TODO: Front end sends verification note
            return Response.getErrorResponse(ResponseError.UNRECOGNIZED_DEVICE);
        }
    }

    @RequestMapping(value = "/{merchantId}/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = {"unchecked"})
    public Response updateMerchantPassword(@PathVariable("merchantId") Long merchantId,
                                           @RequestParam("oldPassword") String oldPassword,
                                           @RequestParam("newPassword") String newPassword,
                                           @RequestParam("token") String tokenString,
                                           @RequestParam("platform") String platformStr,
                                           @RequestParam("deviceToken") String deviceToken,
                                           @RequestParam("ip") Long ip) {
        try {
            final AuthToken authToken = authRoleEntityProcessor.updatePassword(authMerchantService,
                    merchantId,
                    oldPassword,
                    newPassword,
                    tokenString,
                    new AccessInfo(ip, platformStr, deviceToken));

            if (authToken == null) {
                log.error("Can't update password for " + merchantId);
                return Response.FAILED_RESPONSE;
            } else {
                return new Response<AuthToken>(authToken);
            }
        } catch (UnrecognizedDeviceException e) {
            //TODO: Front end sends verification note
            return Response.getErrorResponse(ResponseError.UNRECOGNIZED_DEVICE);
        }
    }

    @RequestMapping(value = "/{merchantId}/logout", method = RequestMethod.POST)
    @ResponseBody
    @SuppressWarnings(value = {"unchecked"})
    public Response doLogoutApi(@PathVariable("merchantId") Long merchantId,
                                @RequestParam("token") String tokenString,
                                @RequestParam("platform") String platformStr,
                                @RequestParam("deviceToken") String deviceToken,
                                @RequestParam("ip") Long ip) {
        try {
            final boolean success = authRoleEntityProcessor.logout(authMerchantService, merchantId, tokenString, new AccessInfo(ip, platformStr, deviceToken));
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