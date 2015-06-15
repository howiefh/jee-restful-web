package io.github.howiefh.jeews.common.shiro;
import io.github.howiefh.jeews.test.entity.TestUser;
import io.github.howiefh.jeews.test.security.realm.TestUserRealm;

import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.SubjectThreadState;
import org.apache.shiro.util.ThreadState;
import org.mockito.Mockito;


/**
 * 在单元测试中的Shiro工具类
 * 
 * @author calvin
 */
public class ShiroTestUtils {

	private static ThreadState threadState;

	/**
	 * 用Mockito快速创建一个已经认证的用户
	 */
	public static void mockSubject(Object principal) {
		Subject subject = Mockito.mock(Subject.class);
		Mockito.when(subject.isAuthenticated()).thenReturn(true);
		Mockito.when(subject.getPrincipal()).thenReturn(principal);
		bindSubject(subject);
	}
	
	public static void mockCurrentUser(TestUser user, boolean isSuperAdmin) {
		Subject subject = Mockito.mock(Subject.class);
		Mockito.when(subject.isAuthenticated()).thenReturn(true);
		Mockito.when(subject.getPrincipal()).thenReturn(user.getUsername());
		SimplePrincipalCollection principals = new SimplePrincipalCollection(user.getUsername(), "root");
		principals.add(user, "root");
		Mockito.when(subject.getPrincipals()).thenReturn(principals);
		Mockito.when(subject.hasRole(TestUserRealm.ADMIN)).thenReturn(isSuperAdmin);
		bindSubject(subject);
	}

	/**
	 * 绑定Subject到当前线程.
	 */
	protected static void bindSubject(Subject subject) {
		clearSubject();
		threadState = new SubjectThreadState(subject);
		threadState.bind();
	}

	/**
	 * 清除当前线程中的Subject.
	 */
	public static void clearSubject() {
		if (threadState != null) {
			threadState.clear();
			threadState = null;
		}
	}
}