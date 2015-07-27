/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 *  @author howiefh
 */
public class BasicEntity extends AbstractEntity<Long>{

	private static final long serialVersionUID = 1575521071086410539L;

	@Override
	public Long getId() {
        return super.getId();
	}

	@Override
	public void setId(Long id) {
        super.setId(id);
	}

	@Override
    @JsonIgnore
	public boolean isNew() {
		// TODO Auto-generated method stub
		return super.isNew();
	}
}
