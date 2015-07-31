/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.github.howiefh.jeews.common.BaseSpringJUnit4Test;
import io.github.howiefh.jeews.common.shiro.ShiroTestUtils;
import io.github.howiefh.jeews.modules.sys.entity.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 *
 *  @author howiefh
 */
public class RoleControllerTest extends BaseSpringJUnit4Test {
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
		        .alwaysDo(print())  //默认每次执行请求后都做的动作
		        .build();

        User user = new User();
        user.setId(1L);
        user.setUsername("root");
        user.setLocked(false);
        ShiroTestUtils.mockSubject(user);
	}

	@After
	public void tearDown() {
		ShiroTestUtils.clearSubject();
	}

	@Test
	public void testGetList() throws Exception {
	    mockMvc.perform(get("/roles")
	            .accept(MediaTypes.HAL_JSON)) //执行请求
	            .andExpect(status().isOk()) //200
	            .andExpect(content().contentType(MediaTypes.HAL_JSON));
	}
}
