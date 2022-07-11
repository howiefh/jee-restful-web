/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License")
 */
package io.github.howiefh.jeews.modules.sys.dao;

import io.github.howiefh.jeews.common.dao.PagingAndSortingDao;
import io.github.howiefh.jeews.modules.sys.entity.Dict;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface DictDao extends PagingAndSortingDao<Dict, Long> {
    public List<String> findTypes(Dict dict);
}