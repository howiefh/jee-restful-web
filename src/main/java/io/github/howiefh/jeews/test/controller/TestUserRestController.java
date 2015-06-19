package io.github.howiefh.jeews.test.controller;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import io.github.howiefh.jeews.test.entity.TestUser;
import io.github.howiefh.jeews.test.resource.TestUserResource;
import io.github.howiefh.jeews.test.resource.TestUserResourceAssembler;
import io.github.howiefh.jeews.test.service.TestUserService;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testusers")
@ExposesResourceFor(TestUser.class)
public class TestUserRestController {
    @Autowired
    private TestUserService userService;

    @Autowired
    private EntityLinks entityLinks;
    
    @Autowired
    private PagedResourcesAssembler<TestUser> assembler;
    
    @RequiresPermissions("test:view")
    @RequestMapping(method = RequestMethod.GET, value = "") 
	public HttpEntity<PagedResources<TestUserResource>> getAll(
	        @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<TestUser> users = userService.findAllByPage(pageable);
	    return new ResponseEntity<>(assembler.toResource(users, new TestUserResourceAssembler()), HttpStatus.OK);
	}
	@RequiresPermissions("test:view")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
	public TestUserResource get(@PathVariable long id) {
        return new TestUserResourceAssembler().toResource(userService.findOne(id));
	}
    @RequiresPermissions("test:create")
	@RequestMapping(value="", method = RequestMethod.POST)
	public ResponseEntity<TestUserResource> create(HttpEntity<TestUser> entity, HttpServletRequest request) throws URISyntaxException {
	    TestUser user = entity.getBody();
	    userService.save(user);
	    HttpHeaders headers = new HttpHeaders();
	    TestUserResource testResource = new TestUserResourceAssembler().toResource(user);
	    //为了 linkForSingleResource 方法可以正常工作，控制器类中需要包含访问单个资源的方法，而且其“@RequestMapping”是类似“/{id}”这样的形式
	    headers.setLocation(entityLinks.linkForSingleResource(TestUser.class, user.getId()).toUri());
	    ResponseEntity<TestUserResource> responseEntity = new ResponseEntity<TestUserResource>(testResource, headers, HttpStatus.CREATED);
	    return responseEntity;
	}
	@RequiresPermissions("test:update")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public TestUserResource update(@RequestBody TestUser user) {
        userService.update(user);
        return new TestUserResourceAssembler().toResource(user);
	}
    
    @RequiresPermissions("test:delete")
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
	    userService.delete(id);
	}
    
    @RequiresPermissions("test:delete")
	@RequestMapping(value="", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAll(@RequestBody List<Long> ids) {
	    userService.deleteAll(ids);
	}
	@RequestMapping(value="/login", method = RequestMethod.POST)
    public void showLoginForm(HttpServletRequest req) {
        String exceptionClassName = (String)req.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String error = "错误";
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
            throw new UnknownAccountException(error);
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
            throw new IncorrectCredentialsException(error);
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
            throw new RuntimeException(error);
        }
	}
    
    @RequiresPermissions("test:view")
    @RequestMapping(value="/{id}/educations", method = RequestMethod.POST)
    public List<String> educationInfo(@PathVariable("id") Long id) {
		return Arrays.asList("XD","TZ");
	}
}