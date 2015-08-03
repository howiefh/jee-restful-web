/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.github.howiefh.jeews.common.BaseSpringJUnit4Test;

import java.util.UUID;

import javax.servlet.ServletException;

import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 *
 *  @author howiefh
 */
public class SignupControllerTest extends BaseSpringJUnit4Test {
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws ServletException {
		AbstractShiroFilter shiroFilter = (AbstractShiroFilter) wac.getBean("shiroFilter");
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				.alwaysDo(print()) // 默认每次执行请求后都做的动作
                .addFilter(shiroFilter, "/*")
				.build();
	}

    @Test
    public void testLogin() throws Exception {
        String requestBody = "{\"username\":\"fh"+UUID.randomUUID()+"\",\"password\":\"123456\",\"email\":\""+UUID.randomUUID()+"@qq.om\"}";
	    mockMvc.perform(post("/signup")
	            .contentType(MediaType.APPLICATION_JSON).content(requestBody)
	            .accept(MediaTypes.HAL_JSON)) //执行请求
	            .andExpect(status().isCreated()) //201
	            .andExpect(jsonPath("$.msg").exists()) //使用Json path验证JSON
	            .andExpect(content().contentType(MediaTypes.HAL_JSON))
	            .andReturn();
    }
}
