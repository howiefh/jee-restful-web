/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.common.entity;

import io.github.howiefh.jeews.test.entity.TestUser;

/**
 *
 *  @author howiefh
 */
public class BasicAuditableEntity extends AbstractAuditableEntity<TestUser, Long>{

	private static final long serialVersionUID = 5911624930541331793L;

	@Override
	public TestUser getCreatedBy() {
		return super.getCreatedBy();
	}

	@Override
	public void setCreatedBy(TestUser createdBy) {
		super.setCreatedBy(createdBy);
	}

	@Override
	public TestUser getLastModifiedBy() {
		return super.getLastModifiedBy();
	}

	@Override
	public void setLastModifiedBy(TestUser lastModifiedBy) {
		super.setLastModifiedBy(lastModifiedBy);
	}
	
}
