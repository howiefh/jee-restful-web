/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */
package io.github.howiefh.jeews.modules.sys.dao;

import org.springframework.stereotype.Repository;

import io.github.howiefh.jeews.common.dao.CrudDao;
import io.github.howiefh.jeews.modules.sys.entity.Organization;

@Repository
public interface OrganizationDao extends CrudDao<Organization, Long>{
}