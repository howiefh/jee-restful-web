/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.oauth2.resource;

import io.github.howiefh.jeews.modules.oauth2.entity.Client;

import org.springframework.hateoas.Resource;

/**
 *  
 *
 *  @author howiefh
 */
public class ClientResource extends Resource<Client>{
    public ClientResource(Client client) {
        super(client);
    }
}
