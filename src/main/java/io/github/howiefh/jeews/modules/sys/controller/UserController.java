package io.github.howiefh.jeews.modules.sys.controller;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import io.github.howiefh.jeews.modules.sys.entity.User;
import io.github.howiefh.jeews.modules.sys.resource.UserResource;
import io.github.howiefh.jeews.modules.sys.resource.UserResourceAssembler;
import io.github.howiefh.jeews.modules.sys.service.UserService;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
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
@RequestMapping("/users")
@ExposesResourceFor(User.class)
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private EntityLinks entityLinks;
    
    @Autowired
    private PagedResourcesAssembler<User> assembler;
    
    @RequiresPermissions("user:view")
    @RequestMapping(value = "", method = RequestMethod.GET) 
	public HttpEntity<PagedResources<UserResource>> getList(
            //@SortDefaults see http://terasolunaorg.github.io/guideline/1.0.x/en/ArchitectureInDetail/Pagination.html#implementation-of-application-layer
	        @PageableDefault(size = 10, page = 0, sort = {"id"}, direction = Direction.ASC) Pageable pageable, 
	        User user) {
        Page<User> users = userService.findPageBy(pageable, user);
	    return new ResponseEntity<>(assembler.toResource(users, new UserResourceAssembler()), HttpStatus.OK);
	}
	@RequiresPermissions("user:view")
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
	public UserResource get(@PathVariable long id) {
        return new UserResourceAssembler().toResource(userService.findOne(id));
	}
    @RequiresPermissions("user:create")
	@RequestMapping(value="", method = RequestMethod.POST)
	public ResponseEntity<UserResource> create(HttpEntity<User> entity, HttpServletRequest request) throws URISyntaxException {
	    User user = entity.getBody();
	    userService.save(user);
	    HttpHeaders headers = new HttpHeaders();
	    UserResource userResource = new UserResourceAssembler().toResource(user);
	    //为了 linkForSingleResource 方法可以正常工作，控制器类中需要包含访问单个资源的方法，而且其“@RequestMapping”是类似“/{id}”这样的形式 不是"{id}"这样
	    headers.setLocation(entityLinks.linkForSingleResource(User.class, user.getId()).toUri());
	    ResponseEntity<UserResource> responseEntity = new ResponseEntity<UserResource>(userResource, headers, HttpStatus.CREATED);
	    return responseEntity;
	}
	@RequiresPermissions("user:update")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public UserResource update(@RequestBody User user) {
        userService.update(user);
        return new UserResourceAssembler().toResource(user);
	}
    
    @RequiresPermissions("user:delete")
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
	    userService.delete(id);
	}
    
    @RequiresPermissions("user:delete")
	@RequestMapping(value="", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBatch(@RequestBody List<Long> ids) {
	    userService.deleteBatch(ids);
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
    
    @RequiresPermissions("user:view")
    @RequestMapping(value="/{id}/educations", method = RequestMethod.POST)
    public List<String> educationInfo(@PathVariable("id") Long id) {
		return Arrays.asList("XD","TZ");
	}
}