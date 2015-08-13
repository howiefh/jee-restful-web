/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.oauth2.controller;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.howiefh.jeews.modules.oauth2.Constants;
import io.github.howiefh.jeews.modules.oauth2.service.OAuthService;
import io.github.howiefh.jeews.modules.sys.service.UserService;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author howiefh
 */
@RestController
public class AccessTokenController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private UserService userService;

    @RequestMapping("/accessToken")
    public HttpEntity<String> token(HttpServletRequest request) throws URISyntaxException, OAuthSystemException {

        try {
            // 构建OAuth请求
            OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);

            // 检查提交的客户端id是否正确
            if (!oAuthService.checkClientId(oauthRequest.getClientId())) {
                return buildInvalidClientIdResponse();

            }

            // 检查客户端安全KEY是否正确
            if (!oAuthService.checkClientSecret(oauthRequest.getClientSecret())) {
                return buildInvalidClientSecretResponse();
            }

            String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
            // 检查验证类型，此处只检查AUTHORIZATION_CODE类型，其他的还有PASSWORD或REFRESH_TOKEN
            if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
                if (!oAuthService.checkAuthCode(authCode)) {
                    return buildBadAuthCodeResponse();
                }
                // TODO 后两种未测试
            } else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.PASSWORD.toString())) {
                if (!checkUserPassword(oauthRequest.getUsername(), oauthRequest.getPassword())) {
                    return buildInvalidUserPassResponse();
                }
            } else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.REFRESH_TOKEN.toString())) {
                // https://github.com/zhouyongtao/homeinns-web
                if (!oAuthService.checkAuthCode(authCode)) {
                    return buildInvalidRefreshTokenResponse();
                }
            }

            // 生成Access Token
            OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
            final String accessToken = oauthIssuerImpl.accessToken();
            oAuthService.addAccessToken(accessToken, oAuthService.getUsernameByAuthCode(authCode));
            final String refreshToken = oauthIssuerImpl.refreshToken();
            oAuthService.addAccessToken(refreshToken, oAuthService.getUsernameByAuthCode(authCode));

            // 生成OAuth响应
            OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken).setExpiresIn(String.valueOf(oAuthService.getExpireIn()))
                    .setTokenType(TokenType.BEARER.toString()).setRefreshToken(refreshToken).buildJSONMessage();

            // 根据OAuthResponse生成ResponseEntity
            return new ResponseEntity<String>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));

        } catch (OAuthProblemException e) {
            // 构建错误响应
            OAuthResponse res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
                    .buildJSONMessage();
            return new ResponseEntity<String>(res.getBody(), HttpStatus.valueOf(res.getResponseStatus()));
        }
    }

    private HttpEntity<String> buildInvalidClientIdResponse() throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_CLIENT)
                .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION).buildJSONMessage();
        return new ResponseEntity<String>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
    }

    private HttpEntity<String> buildInvalidClientSecretResponse() throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
                .setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
                .setErrorDescription(Constants.INVALID_CLIENT_DESCRIPTION).buildJSONMessage();
        return new ResponseEntity<String>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
    }

    private HttpEntity<String> buildBadAuthCodeResponse() throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_GRANT).setErrorDescription(Constants.INVALID_OAUTH_CODE)
                .buildJSONMessage();
        return new ResponseEntity<String>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
    }

    private HttpEntity<String> buildInvalidUserPassResponse() throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_GRANT).setErrorDescription(Constants.INVALID_USER_PASSWORD)
                .buildJSONMessage();
        return new ResponseEntity<String>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
    }

    private HttpEntity<String> buildInvalidRefreshTokenResponse() throws OAuthSystemException {
        OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                .setError(OAuthError.TokenResponse.INVALID_GRANT).setErrorDescription(Constants.INVALID_REFRESH_TOKEN)
                .buildJSONMessage();
        return new ResponseEntity<String>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
    }

    private boolean checkUserPassword(String username, String password) {
        return userService.isValidUser(username, password);
    }
}
