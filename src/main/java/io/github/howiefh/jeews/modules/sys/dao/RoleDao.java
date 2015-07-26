/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */
package io.github.howiefh.jeews.modules.sys.dao;

import io.github.howiefh.jeews.common.dao.CrudDao;
import io.github.howiefh.jeews.modules.sys.entity.Role;

import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudDao<Role, Long>{
	/**
     * 通过名称查找角色
	 * @param name
	 * @return
	 */
	public Role findByName(String name);
	/**
     * 通过中文名称查找角色
	 * @param cnname
	 * @return
	 */
	public Role findByCNName(String cnname);
	/**
	 * 删除角色菜单关联数据
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	/**
	 * 插入角色菜单关联数据
	 * @param role
	 * @return
	 */
	public int saveRoleMenu(Role role);
}