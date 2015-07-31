/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.service;

import io.github.howiefh.jeews.modules.sys.dao.OrganizationDao;
import io.github.howiefh.jeews.modules.sys.entity.Organization;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 *  @author howiefh
 */
@Service
public class OrganizationService {
    @Autowired
    private OrganizationDao organizationDao;

	public void save(Organization organization) {
        organizationDao.save(organization);
	}

	public Organization findOne(long id) {
        return organizationDao.findOne(id);
	}

	public List<Organization> findAll() {
		return organizationDao.findAll();
	}

    public void update(Organization organization) {
		organizationDao.update(organization);
	}

    public int delete(Long id) {
		return organizationDao.delete(id);
	}

    public int deleteBatch(List<Long> ids) {
		return organizationDao.deleteBatch(ids);
	}
}
