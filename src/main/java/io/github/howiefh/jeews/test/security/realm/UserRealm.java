package io.github.howiefh.jeews.test.security.realm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.howiefh.jeews.test.entity.TestUser;
import io.github.howiefh.jeews.test.service.TestUserService;

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
    private TestUserService userService;
    
    private Map<String, String> permissions = new HashMap<String, String>();
    public final static String ADMIN = "admin";
    public final static String USER = "user";
    
    public UserRealm() {
		permissions.put(ADMIN, "test:view,test:create,test:update,test:delete");
		permissions.put(USER, "test:view");
	}
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<String> roles = userService.findByName(username).getRolesList();
        Set<String> roleSet = new HashSet<String>();
        Set<String> permissionSet = new HashSet<String>();
        for (String role : roles) {
			roleSet.add(role);
            for (String permission: permissions.get(role).split(",")) {
                permissionSet.add(permission);
    		}
		}
        authorizationInfo.setRoles(roleSet);
        authorizationInfo.setStringPermissions(permissionSet);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String)token.getPrincipal();

        TestUser user = null;
        try {
            user = userService.findByName(username);
		} catch (Exception e) {
            throw e;
		}

        if(user == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(Boolean.TRUE.equals(user.isLocked())) {
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