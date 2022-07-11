/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.common.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

/**
 * 有分页功能的Dao接口
 *
 * @author howiefh
 */
public interface PagingAndSortingDao<T, ID extends Serializable> extends CrudDao<T, ID> {
    /**
     * 根据分页信息查找实体集合
     *
     * @param pageable
     * @return
     */
    List<T> findPage(@Param("page") Pageable pageable);

    /**
     * 根据分页信息查找符合条件的实体集合
     *
     * @param pageable
     * @param entity
     *            包含查找条件的实体
     * @return
     */
    List<T> findPageBy(@Param("page") Pageable pageable, @Param("param") T entity);
}
