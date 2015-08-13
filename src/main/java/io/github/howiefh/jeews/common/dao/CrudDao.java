/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.common.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.repository.Repository;

/**
 * 增删改查的基本接口
 * 
 * @author howiefh
 */
public interface CrudDao<T, ID extends Serializable> extends Repository<T, ID> {
    /**
     * 将实体存入数据库
     *
     * @param entity
     *            保存的实体
     * @return 返回实体id
     */
    <S extends T> int save(S entity);

    /**
     * 根据id查找实体
     *
     * @param id
     *            不能为 {@literal null}.
     * @return id对应的实体，如果没有找到返回{@literal null}
     */
    T findOne(ID id);

    /**
     * 返回找到的所有实体的集合
     *
     * @return 所有实体
     */
    List<T> findAll();

    /**
     * 找到所有可用的实体的个数
     *
     * @return 实体的个数
     */
    long count();

    /**
     * 找到所有符合条件的实体的个数
     *
     * @param entity
     *            该实体的属性即为条件
     * @return 实体的个数
     */
    long countBy(@Param("param") T entity);

    /**
     * 更新一个实体
     *
     * @param entity
     * @return
     */
    int update(T entity);

    /**
     * 删除id所对应的实体
     *
     * @param id
     *            不能为 {@literal null}.
     * @return
     */
    int delete(ID id);

    /**
     * 批量删除实体
     *
     * @param ids
     *            要删除的实体id集合，不能为 {@literal null}.
     * @return
     */
    int deleteBatch(@Param("ids") List<Long> ids);
}
