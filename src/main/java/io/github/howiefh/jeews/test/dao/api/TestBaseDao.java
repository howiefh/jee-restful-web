package io.github.howiefh.jeews.test.dao.api;

import java.io.Serializable;
import java.util.List;

public interface TestBaseDao<T,PK extends Serializable> {
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	int insert(T entity);
    
	/**
	 * 获取单条数据
	 * @param primaryKey 主键
	 * @return
	 */
	public T get(PK primaryKey);
	
	/**
	 * 查询所有数据列表
	 * @param entity
	 * @return
	 */
	public List<T> findAllList();
    
	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	int update(T entity);
	
	/**
	 * 删除数据
	 * @param id
	 * @return
	 */
	int delete(T entity);
}