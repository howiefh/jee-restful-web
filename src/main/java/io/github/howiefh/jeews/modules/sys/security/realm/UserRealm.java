package io.github.howiefh.jeews.modules.sys.security.realm;

import io.github.howiefh.jeews.modules.sys.entity.Menu;
import io.github.howiefh.jeews.modules.sys.entity.Role;
import io.github.howiefh.jeews.modules.sys.entity.User;
import io.github.howiefh.jeews.modules.sys.service.UserService;

import java.util.HashSet;
import java.util.Set;

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
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = principals.oneByType(SimplePrincipalCollection.class).oneByType(User.class);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

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

        //默认会有一个principal，是username，为了在UserUtils中能获取当前登录的用户，所以在添加一个user
        //http://shiro-user.582556.n2.nabble.com/How-to-set-Principals-td7490972.html
        SimplePrincipalCollection principals = new SimplePrincipalCollection();
        principals.add(username, getName());
        principals.add(user, getName());

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                principals, //用户名，用户
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getSalt()),//salt
                getName()  //realm name
        );
        return authenticationInfo;
    }
}