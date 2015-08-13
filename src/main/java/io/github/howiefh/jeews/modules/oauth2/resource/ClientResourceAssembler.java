/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.oauth2.resource;

import io.github.howiefh.jeews.modules.oauth2.controller.ClientController;
import io.github.howiefh.jeews.modules.oauth2.entity.Client;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 * 
 *
 * @author howiefh
 */
public class ClientResourceAssembler extends ResourceAssemblerSupport<Client, ClientResource> {

    public ClientResourceAssembler() {
        super(ClientController.class, ClientResource.class);
    }

    @Override
    public ClientResource toResource(Client entity) {
        ClientResource resource = createResourceWithId(entity.getId(), entity);
        return resource;
    }

    @Override
    protected ClientResource instantiateResource(Client entity) {
        return new ClientResource(entity);
    }
}