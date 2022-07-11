/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.controller;

import io.github.howiefh.jeews.modules.sys.entity.Organization;
import io.github.howiefh.jeews.modules.sys.resource.OrganizationResource;
import io.github.howiefh.jeews.modules.sys.resource.OrganizationResourceAssembler;
import io.github.howiefh.jeews.modules.sys.resource.ResourcesAssembler;
import io.github.howiefh.jeews.modules.sys.service.OrganizationService;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
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

/**
 *
 *
 * @author howiefh
 */
@RestController
@RequestMapping("/organizations")
@ExposesResourceFor(Organization.class)
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private EntityLinks entityLinks;

    @RequiresPermissions("organization:view")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public HttpEntity<Resources<OrganizationResource>> getList() {
        List<Organization> organizations = organizationService.findAll();
        Resources<OrganizationResource> resources = ResourcesAssembler.toResources(organizations,
                new OrganizationResourceAssembler(), OrganizationController.class);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequiresPermissions("organization:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public OrganizationResource get(@PathVariable long id) {
        return new OrganizationResourceAssembler().toResource(organizationService.findOne(id));
    }

    @RequiresPermissions("organization:create")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<OrganizationResource> create(HttpEntity<Organization> entity, HttpServletRequest request)
            throws URISyntaxException {
        Organization organization = entity.getBody();
        organizationService.save(organization);
        HttpHeaders headers = new HttpHeaders();
        OrganizationResource organizationResource = new OrganizationResourceAssembler().toResource(organization);
        headers.setLocation(entityLinks.linkForSingleResource(Organization.class, organization.getId()).toUri());
        ResponseEntity<OrganizationResource> responseEntity = new ResponseEntity<OrganizationResource>(
                organizationResource, headers, HttpStatus.CREATED);
        return responseEntity;
    }

    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public OrganizationResource update(@RequestBody Organization organization) {
        organizationService.update(organization);
        return new OrganizationResourceAssembler().toResource(organization);
    }

    @RequiresPermissions("organization:delete")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        organizationService.delete(id);
    }

    @RequiresPermissions("organization:delete")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBatch(@RequestBody List<Long> ids) {
        organizationService.deleteBatch(ids);
    }
}
