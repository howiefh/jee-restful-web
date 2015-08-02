/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.util;

import io.github.howiefh.jeews.modules.sys.entity.DataEntity;
import io.github.howiefh.jeews.modules.sys.entity.User;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 *
 *
 *  @author howiefh
 */
public class UserUtils {
	/**
     * 生成DataEntity类中的公共字段
	 * @param entity
	 */
	public static void genCommonFiled(DataEntity entity) {
        User user = getCurrentUser();
        Date date = new Date();
        entity.setCreatedAt(date);
        entity.setCreatedBy(user);
        entity.setUpdatedAt(date);
        entity.setUpdatedBy(user);
	}

	/**
     * 获取当前登陆用户
	 * @return
	 */
	public static User getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        return (User) subject.getPrincipal();
	}
}
