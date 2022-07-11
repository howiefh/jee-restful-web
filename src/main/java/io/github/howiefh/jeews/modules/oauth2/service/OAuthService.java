/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author howiefh
 */
@Service
public class OAuthService {

    private Cache cache;

    @Autowired
    private ClientService clientService;

    @Autowired
    public OAuthService(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("code-cache");
    }

    public void addAuthCode(String authCode, String username) {
        cache.put(authCode, username);
    }

    public void addAccessToken(String accessToken, String username) {
        cache.put(accessToken, username);
    }

    public void addRefreshToken(String refreshToken, String username) {
        cache.put(refreshToken, username);
    }

    public String getUsernameByAuthCode(String authCode) {
        return (String) cache.get(authCode).get();
    }

    public String getUsernameByAccessToken(String accessToken) {
        return (String) cache.get(accessToken).get();
    }

    public boolean checkAuthCode(String authCode) {
        return cache.get(authCode) != null;
    }

    public boolean checkAccessToken(String accessToken) {
        return cache.get(accessToken) != null;
    }

    public boolean checkRefreshToken(String refreshToken) {
        return cache.get(refreshToken) != null;
    }

    public boolean checkClientId(String clientId) {
        return clientService.findByClientId(clientId) != null;
    }

    public boolean checkClientSecret(String clientSecret) {
        return clientService.findByClientSecret(clientSecret) != null;
    }

    public long getExpireIn() {
        return 3600L;
    }
}
