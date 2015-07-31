package io.github.howiefh.jeews.modules.sys.resource;

import io.github.howiefh.jeews.modules.sys.entity.Organization;

import org.springframework.hateoas.Resource;

public class OrganizationResource extends Resource<Organization> {

    public OrganizationResource(Organization organization) {
        super(organization);
    }
}