/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */
package io.github.howiefh.jeews.modules.sys.entity;

import java.util.Set;

import org.springframework.hateoas.core.Relation;

@Relation(value = "role", collectionRelation = "roles")
public class Role extends DataEntity {
	private static final long serialVersionUID = 1744395330761664879L;

	/**
     * 名称 - sys_role.name
     */
    private String name;

    /**
     * 中文名称 - sys_role.cnname
     */
    private String cnname;

    /**
     * 是否可用 - sys_role.available
     */
    private Boolean available;

    /**
     * 角色可以访问的菜单
     */
    private Set<Menu> menus;

	public Role() {
	}

    /**
	 * 构造器，从json整型数据可以构造该对象
	 */
	public Role(Long id) {
        this.id = id;
	}
    /**
     * Gets the value of the database column sys_role.name
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the database column sys_role.name
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * Gets the value of the database column sys_role.cnname
     *
     * @return 中文名称
     */
    public String getCnname() {
        return cnname;
    }

    /**
     * Sets the value of the database column sys_role.cnname
     *
     * @param cnname 中文名称
     */
    public void setCnname(String cnname) {
        this.cnname = cnname == null ? null : cnname.trim();
    }

    /**
     * Gets the value of the database column sys_role.available
     *
     * @return 是否可用
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * Sets the value of the database column sys_role.available
     *
     * @param available 是否可用
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
}