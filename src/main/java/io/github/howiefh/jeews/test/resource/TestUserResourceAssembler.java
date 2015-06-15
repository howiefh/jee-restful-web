package io.github.howiefh.jeews.test.resource;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import io.github.howiefh.jeews.test.controller.TestUserRestController;
import io.github.howiefh.jeews.test.entity.TestUser;

public class TestUserResourceAssembler extends ResourceAssemblerSupport<TestUser, TestUserResource> {

    public TestUserResourceAssembler() {
        super(TestUserRestController.class, TestUserResource.class);
    }

    @Override
    public TestUserResource toResource(TestUser entity) {
    	TestUserResource resource = createResourceWithId(entity.getId(), entity);
        return resource;
    }

    @Override
    protected TestUserResource instantiateResource(TestUser entity) {
        return new TestUserResource(entity);
    }
}