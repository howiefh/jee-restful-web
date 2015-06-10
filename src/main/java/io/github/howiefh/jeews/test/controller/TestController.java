package io.github.howiefh.jeews.test.controller;

import java.util.List;

import io.github.howiefh.jeews.test.entity.TestUser;
import io.github.howiefh.jeews.test.service.TestUserService;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @Autowired
    private TestUserService userService;

    @RequiresPermissions("test:view")
    @RequestMapping(value="", method = RequestMethod.GET)
	public List<TestUser> list() {
		return userService.findAllList();
	}
    @RequiresPermissions("test:view")
    @RequestMapping(value="{id}", method = RequestMethod.GET)
	public TestUser getTestUser(@PathVariable long id) {
        return userService.getOne(id);
	}
    @RequestMapping(value="login", method = RequestMethod.POST)
    public String showLoginForm(HttpServletRequest req) {
        String exceptionClassName = (String)req.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String error = "错误";
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
		return "{msg:" + error +"}";
	}
    
    @RequiresPermissions("test:delete")
    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id) {
        String msg = "成功";
        try {
            int res = userService.delete(userService.getOne(id));
            if (res == 0) {
				msg = "用户不存在";
			}
		} catch (Exception e) {
            msg = "删除失败" + e.getMessage();
		}
		return "{msg:" + msg +"}";
	}
    @RequiresPermissions("test:update")
    @RequestMapping(value="{id}", method = RequestMethod.PUT)
	public String update(@RequestBody TestUser user) {
        String msg = "成功";
        try {
            userService.update(user);
		} catch (Exception e) {
            msg = "更新失败" + e.getMessage();
		}
		return "{msg:" + msg +"}";
	}
    
    @RequiresPermissions("test:create")
    @RequestMapping(value="", method = RequestMethod.POST)
	public String create(@RequestBody TestUser user) {
        String msg = "成功";
        try {
            userService.insert(user);
		} catch (Exception e) {
            msg = "更新失败" + e.getMessage();
		}
		return "{msg:" + msg +"}";
	}
}