/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.oauth2.controller;

import io.github.howiefh.jeews.modules.oauth2.entity.Client;
import io.github.howiefh.jeews.modules.oauth2.resource.ClientResource;
import io.github.howiefh.jeews.modules.oauth2.resource.ClientResourceAssembler;
import io.github.howiefh.jeews.modules.oauth2.service.ClientService;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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

/**
 *
 *
 * @author howiefh
 */
@RestController
@RequestMapping("/clients")
@ExposesResourceFor(Client.class)
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private EntityLinks entityLinks;

    @Autowired
    private PagedResourcesAssembler<Client> assembler;

    @RequiresPermissions("clients:view")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public HttpEntity<PagedResources<ClientResource>> getList(
            @PageableDefault(size = 10, page = 0, sort = {"id"}, direction = Direction.ASC) Pageable pageable,
            Client Client) {
        Page<Client> Clients = clientService.findPageBy(pageable, Client);
        return new ResponseEntity<>(assembler.toResource(Clients, new ClientResourceAssembler()), HttpStatus.OK);
    }

    @RequiresPermissions("clients:view")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ClientResource get(@PathVariable long id) {
        return new ClientResourceAssembler().toResource(clientService.findOne(id));
    }

    @RequiresPermissions("clients:create")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<ClientResource> create(HttpEntity<Client> entity, HttpServletRequest request)
            throws URISyntaxException {
        Client Client = entity.getBody();
        clientService.save(Client);
        HttpHeaders headers = new HttpHeaders();
        ClientResource ClientResource = new ClientResourceAssembler().toResource(Client);
        headers.setLocation(entityLinks.linkForSingleResource(Client.class, Client.getId()).toUri());
        ResponseEntity<ClientResource> responseEntity = new ResponseEntity<ClientResource>(ClientResource, headers,
                HttpStatus.CREATED);
        return responseEntity;
    }

    @RequiresPermissions("clients:update")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ClientResource update(@RequestBody Client client) {
        clientService.update(client);
        return new ClientResourceAssembler().toResource(client);
    }

    @RequiresPermissions("clients:delete")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        clientService.delete(id);
    }

    @RequiresPermissions("clients:delete")
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBatch(@RequestBody List<Long> ids) {
        clientService.deleteBatch(ids);
    }
}