package io.github.howiefh.jeews.modules.sys.resource;

import io.github.howiefh.jeews.modules.sys.controller.UserController;
import io.github.howiefh.jeews.modules.sys.entity.User;

import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResource extends Resource<User> {

    public UserResource(User user) {
        super(user);
        Long userId = user.getId();
        add(linkTo(methodOn(UserController.class).educationInfo(userId)).withRel("educations"));
    }
}