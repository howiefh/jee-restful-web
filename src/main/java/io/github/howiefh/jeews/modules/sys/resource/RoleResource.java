/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.resource;

import io.github.howiefh.jeews.modules.sys.entity.Role;

import org.springframework.hateoas.Resource;

/**
 *
 *
 * @author howiefh
 */
public class RoleResource extends Resource<Role> {

    public RoleResource(Role role) {
        super(role);
    }
}