/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.service;

import io.github.howiefh.jeews.modules.sys.dao.RoleDao;
import io.github.howiefh.jeews.modules.sys.entity.Role;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author howiefh
 */
@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public void save(Role role) {
        roleDao.save(role);
    }

    public Role findOne(long id) {
        return roleDao.findOne(id);
    }

    public Role findByName(String rolename) {
        return roleDao.findByName(rolename);
    }

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public void update(Role role) {
        roleDao.update(role);
    }

    public int delete(Long id) {
        return roleDao.delete(id);
    }

    public int deleteBatch(List<Long> ids) {
        return roleDao.deleteBatch(ids);
    }
}