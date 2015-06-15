package io.github.howiefh.jeews.test.resource;

import io.github.howiefh.jeews.test.controller.TestUserRestController;
import io.github.howiefh.jeews.test.entity.TestUser;

import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class TestUserResource extends Resource<TestUser> {
    
    public TestUserResource(TestUser user) {
        super(user);
        Long userId = user.getId();
        add(linkTo(methodOn(TestUserRestController.class).educationInfo(userId)).withRel("educations"));
    }
}