/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.controller;

import io.github.howiefh.jeews.modules.sys.entity.Role;
import io.github.howiefh.jeews.modules.sys.resource.ResourcesAssembler;
import io.github.howiefh.jeews.modules.sys.resource.RoleResource;
import io.github.howiefh.jeews.modules.sys.resource.RoleResourceAssembler;
import io.github.howiefh.jeews.modules.sys.service.RoleService;

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
@RequestMapping("/roles")
@ExposesResourceFor(Role.class)
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private EntityLinks entityLinks;

    @RequiresPermissions("role:view")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public HttpEntity<Resources<RoleResource>> getList() {
        List<Role> roles = roleService.findAll();
        Resources<RoleResource> resources = ResourcesAssembler.toResources(roles, new RoleResourceAssembler(),
                RoleController.class);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @RequiresPermissions("role:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RoleResource get(@PathVariable long id) {
        return new RoleResourceAssembler().toResource(roleService.findOne(id));
    }

    @RequiresPermissions("role:create")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<RoleResource> create(HttpEntity<Role> entity, HttpServletRequest request)
            throws URISyntaxException {
        Role role = entity.getBody();
        roleService.save(role);
        HttpHeaders headers = new HttpHeaders();
        RoleResource roleResource = new RoleResourceAssembler().toResource(role);
        headers.setLocation(entityLinks.linkForSingleResource(Role.class, role.getId()).toUri());
        ResponseEntity<RoleResource> responseEntity = new ResponseEntity<RoleResource>(roleResource, headers,
                HttpStatus.CREATED);
        return responseEntity;
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RoleResource update(@RequestBody Role role) {
        roleService.update(role);
        return new RoleResourceAssembler().toResource(role);
    }

    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        roleService.delete(id);
    }

    @RequiresPermissions("role:delete")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBatch(@RequestBody List<Long> ids) {
        roleService.deleteBatch(ids);
    }
}