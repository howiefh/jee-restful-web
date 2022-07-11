/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.controller;

import io.github.howiefh.jeews.modules.sys.entity.User;
import io.github.howiefh.jeews.modules.sys.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * @author howiefh
 */
@RestController
@RequestMapping("signup")
public class SignupController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> signup(@RequestBody User u) {
        u.setLocked(true);

        userService.save(u);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("msg", "谢谢您注册，目前新用户账号暂时被锁定，可以联系管理员解锁！");
        return map;
    }
}