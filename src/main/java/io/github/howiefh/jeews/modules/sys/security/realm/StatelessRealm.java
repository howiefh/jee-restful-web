/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.security.realm;

import io.github.howiefh.jeews.modules.sys.entity.User;
import io.github.howiefh.jeews.modules.sys.entity.User.RolePermission;
import io.github.howiefh.jeews.modules.sys.security.token.JsonWebToken;
import io.github.howiefh.jeews.modules.sys.service.UserService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 *
 * @author howiefh
 */
public class StatelessRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        // 仅支持JsonWebToken类型的Token
        return token instanceof JsonWebToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 根据用户名查找角色，请根据需求实现
        String username = (String) principals.getPrimaryPrincipal();

        User user = userService.findByName(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principals;
        principalCollection.clear();
        principalCollection.add(user, getName());

        RolePermission rolePermission = user.new RolePermission();
        authorizationInfo.setRoles(rolePermission.getRoleSet());
        authorizationInfo.setStringPermissions(rolePermission.getPermissionSet());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JsonWebToken jsonWebToken = (JsonWebToken) token;
        String jwt = jsonWebToken.getToken();
        // 然后进行客户端消息摘要和服务器端消息摘要的匹配
        return new SimpleAuthenticationInfo("", jwt, getName());
    }
}