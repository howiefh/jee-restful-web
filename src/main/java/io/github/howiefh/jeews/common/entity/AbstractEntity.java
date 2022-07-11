/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.common.entity;

import java.io.Serializable;

import org.springframework.data.domain.Persistable;

/**
 * @param <ID>
 *            the primary key
 *
 * @author howiefh
 */
public abstract class AbstractEntity<ID extends Serializable> implements Persistable<ID> {

    private static final long serialVersionUID = -1673249709005412262L;

    protected ID id;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.domain.Persistable#getId()
     */
    @Override
    public ID getId() {
        return id;
    }

    /**
     * Set the id of the entity
     *
     * @param id
     */
    public void setId(ID id) {
        this.id = id;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.domain.Persistable#isNew()
     */
    @Override
    public boolean isNew() {
        return null == getId();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Entity " + getId());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof AbstractEntity<?>)) {
            return false;
        }

        AbstractEntity<?> that = (AbstractEntity<?>) obj;

        return null == this.getId() ? false : this.getId().equals(that.getId());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int result = 17;

        result += null == getId() ? 0 : getId().hashCode() * 31;

        return result;
    }
}