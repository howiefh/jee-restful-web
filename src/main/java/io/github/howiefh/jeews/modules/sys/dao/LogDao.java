/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License")
 */
package io.github.howiefh.jeews.modules.sys.dao;

import org.springframework.stereotype.Repository;

import io.github.howiefh.jeews.common.dao.PagingAndSortingDao;
import io.github.howiefh.jeews.modules.sys.entity.Log;

@Repository
public interface LogDao extends PagingAndSortingDao<Log, Long> {
}