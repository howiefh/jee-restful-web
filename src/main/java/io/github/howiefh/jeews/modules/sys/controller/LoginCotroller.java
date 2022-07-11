/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.controller;

import io.github.howiefh.jeews.modules.sys.entity.User;
import io.github.howiefh.jeews.modules.sys.entity.User.RolePermission;
import io.github.howiefh.jeews.modules.sys.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTSigner.Options;

/**
 *
 *
 * @author howiefh
 */
@RestController
@RequestMapping("/login")
public class LoginCotroller {
    @Value("${jwt.secret}")
    private String secret;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody User u) {
        String username = u.getUsername();
        String password = u.getPassword();
        if (username == null) {
            throw new NullPointerException("用户名或密码错误！");
        }
        User user = userService.findByName(username);

        if (user == null) {
            throw new UnknownAccountException("该用户不存在！");// 没找到帐号
        }

        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException("账号已被锁定！"); // 帐号锁定
        }

        if (!userService.passwordsMatch(user, password)) {
            throw new IncorrectCredentialsException("用户名或密码错误！");
        }

        JWTSigner signer = new JWTSigner(secret);
        Options options = new Options();
        // 7 * 24 * 60 * 60 = 604800
        options.setExpirySeconds(604800);
        Map<String, Object> claims = new HashMap<String, Object>();
        RolePermission rolePermission = user.new RolePermission();
        claims.put("perms", rolePermission.getPermissionSet());
        claims.put("iss", user.getUsername());
        String token = signer.sign(claims, options);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", token);
        Map<String, Object> userMap = new HashMap<String, Object>();
        userMap.put("id", user.getId());
        userMap.put("username", user.getUsername());
        userMap.put("perms", rolePermission.getPermissionSet());
        userMap.put("roles", rolePermission.getRoleSet());
        map.put("user", userMap);
        return map;
    }
}
