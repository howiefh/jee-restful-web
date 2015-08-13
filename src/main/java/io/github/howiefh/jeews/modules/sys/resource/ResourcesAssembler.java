/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

/**
 *
 *
 * @author howiefh
 */
public class ResourcesAssembler {
    /**
     * 将实体集entities转化为Resources
     * 
     * @param entities
     * @param assembler
     * @param clazz
     * @return
     */
    public static <T, D extends ResourceSupport> Resources<D> toResources(Iterable<T> entities,
            ResourceAssemblerSupport<T, D> assembler, Class<?> clazz) {
        Link link = linkTo(clazz).withSelfRel();
        Resources<D> resources = new Resources<>(assembler.toResources(entities), link);
        return resources;
    }
}
