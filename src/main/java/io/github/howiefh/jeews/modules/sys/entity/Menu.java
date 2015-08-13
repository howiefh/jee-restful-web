/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */
package io.github.howiefh.jeews.modules.sys.entity;

public class Menu extends DataEntity {
    private static final long serialVersionUID = -8621378366479658296L;

    /**
     * 名称 - sys_menu.name
     */
    private String name;

    /**
     * 权限标识 - sys_menu.permission
     */
    private String permission;

    /**
     * 链接 - sys_menu.url
     */
    private String url;

    /**
     * 父级编号 - sys_menu.parent_id
     */
    private Long parentId;

    /**
     * 所有父级编号 - sys_menu.parent_ids
     */
    private String parentIds;

    /**
     * 排序 - sys_menu.sort
     */
    private Integer sort;

    /**
     * 图标 - sys_menu.icon
     */
    private String icon;

    /**
     * 是否显示 - sys_menu.is_show
     */
    private Boolean isShow;

    /**
     * Gets the value of the database column sys_menu.name
     *
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the database column sys_menu.name
     *
     * @param name
     *            名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * Gets the value of the database column sys_menu.permission
     *
     * @return 权限标识
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Sets the value of the database column sys_menu.permission
     *
     * @param permission
     *            权限标识
     */
    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    /**
     * Gets the value of the database column sys_menu.url
     *
     * @return 链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the database column sys_menu.url
     *
     * @param url
     *            链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * Gets the value of the database column sys_menu.parent_id
     *
     * @return 父级编号
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Sets the value of the database column sys_menu.parent_id
     *
     * @param parentId
     *            父级编号
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * Gets the value of the database column sys_menu.parent_ids
     *
     * @return 所有父级编号
     */
    public String getParentIds() {
        return parentIds;
    }

    /**
     * Sets the value of the database column sys_menu.parent_ids
     *
     * @param parentIds
     *            所有父级编号
     */
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds == null ? null : parentIds.trim();
    }

    /**
     * Gets the value of the database column sys_menu.sort
     *
     * @return 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * Sets the value of the database column sys_menu.sort
     *
     * @param sort
     *            排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * Gets the value of the database column sys_menu.icon
     *
     * @return 图标
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets the value of the database column sys_menu.icon
     *
     * @param icon
     *            图标
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * Gets the value of the database column sys_menu.is_show
     *
     * @return 是否显示
     */
    public Boolean getIsShow() {
        return isShow;
    }

    /**
     * Sets the value of the database column sys_menu.is_show
     *
     * @param isShow
     *            是否显示
     */
    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }
}