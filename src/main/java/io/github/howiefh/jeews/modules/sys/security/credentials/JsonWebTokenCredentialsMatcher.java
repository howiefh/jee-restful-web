/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.security.credentials;

import io.github.howiefh.jeews.modules.sys.security.token.JsonWebToken;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

/**
 *
 *
 * @author howiefh
 */
public class JsonWebTokenCredentialsMatcher implements CredentialsMatcher {
    private static final Logger log = LoggerFactory.getLogger(JsonWebTokenCredentialsMatcher.class);
    private String audience;
    private String secret;

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.shiro.authc.credential.CredentialsMatcher#doCredentialsMatch
     * (org.apache.shiro.authc.AuthenticationToken,
     * org.apache.shiro.authc.AuthenticationInfo)
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        JsonWebToken jsonWebToken = (JsonWebToken) token;
        JWTVerifier verifier = new JWTVerifier(secret, audience);
        try {
            Map<String, Object> map = verifier.verify(jsonWebToken.getToken());
            SimpleAuthenticationInfo authenticationInfo = (SimpleAuthenticationInfo) info;
            String realmName = authenticationInfo.getPrincipals().getRealmNames().iterator().next();
            SimplePrincipalCollection principals = new SimplePrincipalCollection();
            principals.add(map.get("iss"), realmName);
            authenticationInfo.setPrincipals(principals);
            return true;
        } catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException | SignatureException
                | IOException | JWTVerifyException e) {
            log.debug(e.getMessage());
            return false;
        }
    }
}
