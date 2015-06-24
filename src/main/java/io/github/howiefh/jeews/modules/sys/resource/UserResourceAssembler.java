package io.github.howiefh.jeews.modules.sys.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import io.github.howiefh.jeews.modules.sys.controller.UserController;
import io.github.howiefh.jeews.modules.sys.entity.User;

public class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    public UserResourceAssembler() {
        super(UserController.class, UserResource.class);
    }

    @Override
    public UserResource toResource(User entity) {
    	UserResource resource = createResourceWithId(entity.getId(), entity);
        return resource;
    }

    @Override
    protected UserResource instantiateResource(User entity) {
        return new UserResource(entity);
    }
}