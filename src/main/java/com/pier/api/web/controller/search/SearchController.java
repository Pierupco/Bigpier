package com.pier.api.web.controller.search;

import com.pier.api.web.response.Response;
import com.pier.api.web.response.ResponseError;
import com.pier.application.model.User;
import com.pier.application.model.search.MerchantSearchResult;
import com.pier.application.model.security.AccessInfo;
import com.pier.application.processor.AuthRoleEntityProcessor;
import com.pier.application.service.search.SearchService;
import com.pier.application.service.security.AuthUserService;
import com.pier.application.service.security.token.exception.UnrecognizedDeviceException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final AuthUserService authUserService;
    private final AuthRoleEntityProcessor authRoleEntityProcessor;
    private final SearchService searchService;

    @Autowired
    public SearchController(AuthUserService authUserService, AuthRoleEntityProcessor authRoleEntityProcessor, SearchService searchService) {
        this.authUserService = authUserService;
        this.authRoleEntityProcessor = authRoleEntityProcessor;
        this.searchService = searchService;
    }

    /**
     * Do Search, throw internal io exception, front end will get 500
     *
     * @param merchantName
     * @param userId
     * @param tokenString
     * @param platformStr
     * @param deviceToken
     * @param ip
     * @return
     * @throws IOException
     */
    @RequestMapping("/merchant/name/{name}")
    public Response doSearchMerchantName(@PathVariable("name") String merchantName,
                                         @RequestParam("userId") Long userId,
                                         @RequestParam("token") String tokenString,
                                         @RequestParam("platform") String platformStr,
                                         @RequestParam("deviceToken") String deviceToken,
                                         @RequestParam("ip") Long ip) throws IOException, ParseException {
        try {
            final User user = (User) authRoleEntityProcessor.verifyAndGetRoleEntity(authUserService, userId, tokenString, new AccessInfo(ip, platformStr, deviceToken));
            if (user != null) {
                final List<MerchantSearchResult> merchantList = searchService.searchMerchantsByName(merchantName);
                return new Response<List>(merchantList);
            } else {
                return Response.FAILED_RESPONSE;
            }
        } catch (UnrecognizedDeviceException e) {
            return Response.getErrorResponse(ResponseError.UNRECOGNIZED_DEVICE);
        }
    }
}
