package io.github.howiefh.jeews.modules.sys.security.realm;

import java.util.HashSet;
import java.util.Set;

import io.github.howiefh.jeews.modules.sys.entity.Menu;
import io.github.howiefh.jeews.modules.sys.entity.Role;
import io.github.howiefh.jeews.modules.sys.entity.User;
import io.github.howiefh.jeews.modules.sys.service.UserService;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;  
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = userService.findByName(username);
        Set<Role> roles = user.getRoles();
        Set<String> permissionSet = new HashSet<String>();
        Set<String> roleSet = new HashSet<String>();
        for (Role role : roles) {
			roleSet.add(role.getName());
            Set<Menu> menus = role.getMenus();
            for (Menu menu : menus) {
                permissionSet.add(menu.getPermission());
			}
		}
        authorizationInfo.setRoles(roleSet);
        authorizationInfo.setStringPermissions(permissionSet);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();

        User user = userService.findByName(username);

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); //帐号锁定
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getUsername(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getSalt()),//salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}