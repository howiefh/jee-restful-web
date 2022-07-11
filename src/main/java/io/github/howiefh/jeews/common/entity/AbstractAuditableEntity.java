/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.common.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * @param <U>
 *            the user
 * @param <ID>
 *            the primary key
 *
 * @author howiefh
 */
public abstract class AbstractAuditableEntity<U, ID extends Serializable> extends AbstractEntity<ID> {
    private static final long serialVersionUID = -1562785370056166597L;

    @CreatedBy
    protected U createdBy;

    @CreatedDate
    protected Date createdDate;

    @LastModifiedBy
    protected U lastModifiedBy;

    @LastModifiedDate
    protected Date lastModifiedDate;

    /**
     * Returns the user who created this entity.
     *
     * @return the createdBy
     */
    public U getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the user who created this entity.
     *
     * @param createdBy
     *            the creating entity to set
     */
    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Returns the creation date of the entity.
     *
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the creation date of the entity.
     *
     * @param creationDate
     *            the creation date to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Returns the user who modified the entity lastly.
     *
     * @return the lastModifiedBy
     */
    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * Sets the user who modified the entity lastly.
     *
     * @param lastModifiedBy
     *            the last modifying entity to set
     */
    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * Returns the date of the last modification.
     *
     * @return the lastModifiedDate
     */
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * Sets the date of the last modification.
     *
     * @param lastModifiedDate
     *            the date of the last modification to set
     */
    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
