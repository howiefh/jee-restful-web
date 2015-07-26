/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import io.github.howiefh.jeews.common.BaseSpringJUnit4Test;
import io.github.howiefh.jeews.modules.sys.entity.User;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

/**
 *
 *
 *  @author howiefh
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest extends BaseSpringJUnit4Test {
    @Autowired
    private UserDao userDao;
    private static Long id;
    @Test
    public void test1Save() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("8eb86cd938cd1ecbe9b7316859d4da53");
        user.setSalt("b1c33fa8919f5f6378e4fc5e3eb839af");
        user.setEmail("user@gmail.com");

        userDao.save(user);
        id = user.getId();
        assertNotNull(id);
    }

    @Test
    public void test2FindOne() {
        User user = userDao.findOne(1L);
        assertNotNull(user.getRoles());
        assertNotEquals(user.getRoles().size(), 1);
	}

    @Test
    public void test2FindByPage() {
        User u = new User();
        u.setUsername("root");
        u.setEmail("1");
        List<User> users = userDao.findPageBy(new PageRequest(0, 10, Direction.ASC, "id"), u);
        assertEquals(users.size(), 1);
	}

    @Test
    public void test3Update() {
        User user = userDao.findOne(id);
        String email = "1234@qq.com";
        user.setEmail(email);
        user = userDao.findOne(id);
        user.setEmail(email);
        userDao.update(user);
        assertEquals(user.getEmail(), email);
	}

    @Test
    public void test4Delete() {
        userDao.delete(id);
        User user = userDao.findOne(id);
        assertNull(user);
	}
}
