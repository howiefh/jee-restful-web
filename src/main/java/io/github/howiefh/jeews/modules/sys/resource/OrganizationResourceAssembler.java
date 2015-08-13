package io.github.howiefh.jeews.modules.sys.resource;

import io.github.howiefh.jeews.modules.sys.controller.OrganizationController;
import io.github.howiefh.jeews.modules.sys.entity.Organization;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class OrganizationResourceAssembler extends ResourceAssemblerSupport<Organization, OrganizationResource> {

    public OrganizationResourceAssembler() {
        super(OrganizationController.class, OrganizationResource.class);
    }

    @Override
    public OrganizationResource toResource(Organization entity) {
        OrganizationResource resource = createResourceWithId(entity.getId(), entity);
        return resource;
    }

    @Override
    protected OrganizationResource instantiateResource(Organization entity) {
        return new OrganizationResource(entity);
    }
}