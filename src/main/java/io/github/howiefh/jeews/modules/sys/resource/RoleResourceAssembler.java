package io.github.howiefh.jeews.modules.sys.resource;

import io.github.howiefh.jeews.modules.sys.controller.RoleController;
import io.github.howiefh.jeews.modules.sys.entity.Role;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class RoleResourceAssembler extends ResourceAssemblerSupport<Role, RoleResource> {

    public RoleResourceAssembler() {
        super(RoleController.class, RoleResource.class);
    }

    @Override
    public RoleResource toResource(Role entity) {
        RoleResource resource = createResourceWithId(entity.getId(), entity);
        return resource;
    }

    @Override
    protected RoleResource instantiateResource(Role entity) {
        return new RoleResource(entity);
    }
}