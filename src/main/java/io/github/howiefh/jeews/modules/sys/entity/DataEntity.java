/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.entity;

import io.github.howiefh.jeews.common.entity.BasicEntity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 *  @author howiefh
 */
public class DataEntity extends BasicEntity {
	private static final long serialVersionUID = 2480441011481699886L;

	/**
	 * 创建者
	 */
	private User createdBy;
	/**
	 * 创建时间
	 */
	private Date createdAt;
	/**
	 * 更新者
	 */
	private User updatedBy;
	/**
	 * 更新时间
	 */
	private Date updatedAt;

    @JsonIgnore
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
    @JsonIgnore
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
    @JsonIgnore
	public User getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
    @JsonIgnore
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}