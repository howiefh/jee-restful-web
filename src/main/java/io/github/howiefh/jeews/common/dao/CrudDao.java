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
 *
 * @author howiefh
 */
public interface CrudDao<T,ID extends Serializable> extends Repository<T, ID>{
	/**
	 * Saves a given entity. 
	 * 
	 * @param entity
	 * @return
	 */
	<S extends T> int save(S entity);

	/**
	 * Retrieves an entity by its id.
	 * 
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal null} if none found
	 */
	T findOne(ID id);

	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */
	List<T> findAll();

	/**
	 * Returns the number of entities available.
	 * 
	 * @return the number of entities
	 */
	long count();
    
	/**
	 * Returns the number of entities available.
     * 
	 * @param entity
	 * @return the number of entities
	 */
	long countBy(@Param("param") T entity);

	/**
	 * Updates a given entity.
	 * 
	 * @param entity
	 * @return
	 */
	int update(T entity);
    
	/**
	 * Deletes the entity with the given id.
	 * 
	 * @param id must not be {@literal null}.
	 * @return
	 */
	int delete(ID id);
}
