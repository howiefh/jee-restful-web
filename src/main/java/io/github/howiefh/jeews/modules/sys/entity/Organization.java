/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */
package io.github.howiefh.jeews.modules.sys.entity;

import org.springframework.hateoas.core.Relation;


@Relation(value = "organization", collectionRelation = "organizations")
public class Organization extends DataEntity {
	private static final long serialVersionUID = -5397696221970296107L;

    /**
     * 名称 - sys_organization.name
     */
    private String name;

    /**
     * 类型 - sys_organization.type
     */
    private String type;

    /**
     * 父级编号 - sys_organization.parent_id
     */
    private Long parentId;

    /**
     * 所有父级编号 - sys_organization.parent_ids
     */
    private String parentIds;

    /**
     * 排序 - sys_organization.sort
     */
    private Integer sort;

    /**
     * 是否显示 - sys_organization.is_show
     */
    private Boolean isShow;

    public Organization() {
	}

    /**
	 * 构造器，从json整型数据可以构造该对象
	 */
	public Organization(Long id) {
        this.id = id;
	}
    /**
     * Gets the value of the database column sys_organization.name
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the database column sys_organization.name
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * Gets the value of the database column sys_organization.type
     *
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the database column sys_organization.type
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * Gets the value of the database column sys_organization.parent_id
     *
     * @return 父级编号
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Sets the value of the database column sys_organization.parent_id
     *
     * @param parentId 父级编号
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * Gets the value of the database column sys_organization.parent_ids
     *
     * @return 所有父级编号
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * Sets the value of the database column sys_organization.parent_ids
     *
     * @param parentIds 所有父级编号
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    /**
     * Gets the value of the database column sys_organization.sort
     *
     * @return 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * Sets the value of the database column sys_organization.sort
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * Gets the value of the database column sys_organization.is_show
     *
     * @return 是否显示
     */
    public Boolean getIsShow() {
        return isShow;
    }

    /**
     * Sets the value of the database column sys_organization.is_show
     *
     * @param isShow 是否显示
     */
    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }
}