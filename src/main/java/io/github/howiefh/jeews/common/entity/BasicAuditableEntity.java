/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.common.entity;

import io.github.howiefh.jeews.modules.sys.entity.User;

/**
 *
 * @author howiefh
 */
public class BasicAuditableEntity extends AbstractAuditableEntity<User, Long> {

    private static final long serialVersionUID = 5911624930541331793L;

    @Override
    public User getCreatedBy() {
        return super.getCreatedBy();
    }

    @Override
    public void setCreatedBy(User createdBy) {
        super.setCreatedBy(createdBy);
    }

    @Override
    public User getLastModifiedBy() {
        return super.getLastModifiedBy();
    }

    @Override
    public void setLastModifiedBy(User lastModifiedBy) {
        super.setLastModifiedBy(lastModifiedBy);
    }

}
