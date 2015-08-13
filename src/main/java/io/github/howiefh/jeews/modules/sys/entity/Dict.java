/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */
package io.github.howiefh.jeews.modules.sys.entity;

public class Dict extends DataEntity {
    private static final long serialVersionUID = -8773829955526770483L;

    /**
     * 数据值 - sys_dict.value
     */
    private String value;

    /**
     * 标签名 - sys_dict.label
     */
    private String label;

    /**
     * 类型 - sys_dict.type
     */
    private String type;

    /**
     * 描述 - sys_dict.description
     */
    private String description;

    /**
     * Gets the value of the database column sys_dict.value
     *
     * @return 数据值
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the database column sys_dict.value
     *
     * @param value
     *            数据值
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * Gets the value of the database column sys_dict.label
     *
     * @return 标签名
     */
    public String getLabel() {
        return label;
    }

    /**
     * Sets the value of the database column sys_dict.label
     *
     * @param label
     *            标签名
     */
    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    /**
     * Gets the value of the database column sys_dict.type
     *
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the database column sys_dict.type
     *
     * @param type
     *            类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * Gets the value of the database column sys_dict.description
     *
     * @return 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the database column sys_dict.description
     *
     * @param description
     *            描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}