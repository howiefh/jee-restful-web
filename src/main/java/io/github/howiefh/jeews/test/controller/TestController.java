package io.github.howiefh.jeews.test.controller;

import java.util.List;

import io.github.howiefh.jeews.test.entity.TestUser;
import io.github.howiefh.jeews.test.security.realm.UserRealm;
import io.github.howiefh.jeews.test.service.TestUserService;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("test")
public class TestController {
	private Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private TestUserService userService;

    @RequestMapping("index")
	public String index() {
		return "test/index";
	}
    @RequiresPermissions("test:view")
    @RequestMapping(value="list", method = RequestMethod.GET)
	public String list(Model model, String msg) {
        model.addAttribute("users", userService.findAllList());
        model.addAttribute("msg", msg);
		return "test/list";
	}
    @RequestMapping(value="login", method = RequestMethod.GET)
	public String login(HttpServletRequest request,Model model) {
        logger.debug("login");
		return "test/login";
	}
    @RequestMapping(value="login", method = RequestMethod.POST)
    public String showLoginForm(HttpServletRequest req, Model model) {
        String exceptionClassName = (String)req.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
		return "test/login";
	}
    
    @RequiresPermissions("test:delete")
    @RequestMapping(value="{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes ) {
        try {
            userService.delete(userService.getOne(id));
		} catch (Exception e) {
            redirectAttributes.addAttribute("msg", "删除失败:" + e.getMessage());
    		return "redirect:/test/list";
		}
        redirectAttributes.addAttribute("msg", "删除成功");
		return "redirect:/test/list";
	}
    @RequiresPermissions("test:update")
    @RequestMapping(value="{id}/update", method = RequestMethod.GET)
	public String update(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roleList", new String[]{UserRealm.ADMIN,UserRealm.USER});
        model.addAttribute("user", userService.getOne(id));
        model.addAttribute("act", "update");
		return "test/edit";
	}
    @RequiresPermissions("test:update")
    @RequestMapping(value="{id}/update", method = RequestMethod.POST)
	public String update(TestUser user, @RequestParam("roles") List<String> roleList
			, RedirectAttributes redirectAttributes) {
        genRoles(user, roleList);
        encryptPassword(user);
        try {
            userService.update(user);
		} catch (Exception e) {
            redirectAttributes.addAttribute("msg", "更新失败:" + e.getMessage());
    		return "redirect:/test/list";
		}
        redirectAttributes.addAttribute("msg", "更新成功");
		return "redirect:/test/list";
	}
    
    @RequiresPermissions("test:create")
    @RequestMapping(value="create", method = RequestMethod.GET)
	public String create(Model model) {
        model.addAttribute("roleList", new String[]{"admin","user"});
        model.addAttribute("user", new TestUser());
        model.addAttribute("act", "create");
		return "test/edit";
	}
    @RequiresPermissions("test:create")
    @RequestMapping(value="create", method = RequestMethod.POST)
	public String create(TestUser user, @RequestParam("roles") List<String> roleList
			, RedirectAttributes redirectAttributes) {
        genRoles(user, roleList);
        encryptPassword(user);
        try {
            userService.insert(user);
		} catch (Exception e) {
            redirectAttributes.addAttribute("msg", "增加失败:" + e.getMessage());
    		return "redirect:/test/list";
		}
        redirectAttributes.addAttribute("msg", "增加成功");
		return "redirect:/test/list";
	}
    
    private void encryptPassword(TestUser user) {
        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        generator.setSeed("abc".getBytes());
        String salt = generator.nextBytes().toHex();
        
        DefaultHashService hashService = new DefaultHashService();
		HashRequest request = new HashRequest.Builder()  
		            .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes(user.getPassword()))  
		            .setSalt(ByteSource.Util.bytes(salt)).setIterations(2).build();  
		String pwd = hashService.computeHash(request).toHex();
        
        user.setSalt(salt);
        user.setPassword(pwd);
	}
    
    private void genRoles(TestUser user, List<String> roleList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : roleList) {
			stringBuilder.append(string);
			stringBuilder.append(",");
		}
        user.setRoles(stringBuilder.toString());
	}
}