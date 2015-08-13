/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 */
package io.github.howiefh.jeews.modules.sys.entity;

import io.github.howiefh.jeews.common.entity.BasicEntity;

import java.util.Date;

public class Log extends BasicEntity {
    private static final long serialVersionUID = 450382404560336502L;

    /**
     * 日志类型 - sys_log.type
     */
    private String type;

    /**
     * 日志标题 - sys_log.title
     */
    private String title;

    /**
     * 创建者编号 - sys_log.create_by
     */
    private Long createBy;

    /**
     * 创建时间 - sys_log.create_date
     */
    private Date createDate;

    /**
     * 操作IP地址 - sys_log.remote_addr
     */
    private String remoteAddr;

    /**
     * 用户代理 - sys_log.user_agent
     */
    private String userAgent;

    /**
     * 请求URI - sys_log.request_uri
     */
    private String requestUri;

    /**
     * 操作方式 - sys_log.method
     */
    private String method;

    /**
     * 异常信息 - sys_log.exception
     */
    private String exception;

    /**
     * Gets the value of the database column sys_log.type
     *
     * @return 日志类型
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the database column sys_log.type
     *
     * @param type
     *            日志类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * Gets the value of the database column sys_log.title
     *
     * @return 日志标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the database column sys_log.title
     *
     * @param title
     *            日志标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * Gets the value of the database column sys_log.create_by
     *
     * @return 创建者编号
     */
    public Long getCreateBy() {
        return createBy;
    }

    /**
     * Sets the value of the database column sys_log.create_by
     *
     * @param createBy
     *            创建者编号
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    /**
     * Gets the value of the database column sys_log.create_date
     *
     * @return 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Sets the value of the database column sys_log.create_date
     *
     * @param createDate
     *            创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the value of the database column sys_log.remote_addr
     *
     * @return 操作IP地址
     */
    public String getRemoteAddr() {
        return remoteAddr;
    }

    /**
     * Sets the value of the database column sys_log.remote_addr
     *
     * @param remoteAddr
     *            操作IP地址
     */
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr == null ? null : remoteAddr.trim();
    }

    /**
     * Gets the value of the database column sys_log.user_agent
     *
     * @return 用户代理
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Sets the value of the database column sys_log.user_agent
     *
     * @param userAgent
     *            用户代理
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent == null ? null : userAgent.trim();
    }

    /**
     * Gets the value of the database column sys_log.request_uri
     *
     * @return 请求URI
     */
    public String getRequestUri() {
        return requestUri;
    }

    /**
     * Sets the value of the database column sys_log.request_uri
     *
     * @param requestUri
     *            请求URI
     */
    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri == null ? null : requestUri.trim();
    }

    /**
     * Gets the value of the database column sys_log.method
     *
     * @return 操作方式
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the value of the database column sys_log.method
     *
     * @param method
     *            操作方式
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * Gets the value of the database column sys_log.exception
     *
     * @return 异常信息
     */
    public String getException() {
        return exception;
    }

    /**
     * Sets the value of the database column sys_log.exception
     *
     * @param exception
     *            异常信息
     */
    public void setException(String exception) {
        this.exception = exception == null ? null : exception.trim();
    }
}