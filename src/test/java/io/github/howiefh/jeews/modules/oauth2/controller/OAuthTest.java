/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.oauth2.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import io.github.howiefh.jeews.common.BaseSpringJUnit4Test;
import io.github.howiefh.jeews.common.shiro.ShiroTestUtils;
import io.github.howiefh.jeews.modules.oauth2.shiro.filter.TokenFilter;
import io.github.howiefh.jeews.modules.sys.entity.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 *
 * @author howiefh
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class OAuthTest extends BaseSpringJUnit4Test {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws ServletException {
        TokenFilter tokenFilter = (TokenFilter) wac.getBean("tokenFilter");
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).alwaysDo(print()) // 默认每次执行请求后都做的动作
                // 这里不加shiro过滤器的话testAuth最后获取用户信息不会被过滤,尽管配置文件中有配置
                // 但是如果是添加
                // AbstractShiroFilter shiroFilter = (AbstractShiroFilter)
                // wac.getBean("shiroFilter");这个过滤器的话
                // 控制器里的@RequiresPermissions注解要去掉,否则抛异常.貌似ShiroTestUtils没起作用,运行过程中的DelegatingSubject和模拟的不是一个
                // http://stackoverflow.com/questions/22152530/spring-end-to-end-test-including-filters-and-controller-method-using-mockhttpser
                .addFilter(tokenFilter, "/users/1").build();
        User user = new User();
        user.setUsername("root");
        user.setLocked(false);
        ShiroTestUtils.mockCurrentUser(user, true);

        // 没有没有下面这几句,tokenfilter也不会工作
        AbstractShiroFilter shiroFilter = (AbstractShiroFilter) wac.getBean("shiroFilter");
        FilterConfig mockFilterConfig = mock(FilterConfig.class);
        shiroFilter.init(mockFilterConfig);
    }

    @After
    public void tearDown() {
        ShiroTestUtils.clearSubject();
    }

    String clientID = "c1ebe466-1cdc-4bd3-ab69-77c3561b9dee";
    String clientSecret = "d8346ea2-6017-43ed-ad68-19c0f971738b";
    String responseType = ResponseType.CODE.toString();
    String redirectUri = "http://localhost:9080/chapter17-client/oauth2-login";
    String grantType = GrantType.AUTHORIZATION_CODE.toString();

    @Test
    public void testFilter() {
        AbstractShiroFilter shiroFilter = (AbstractShiroFilter) wac.getBean("shiroFilter");
        PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter
                .getFilterChainResolver();
        DefaultFilterChainManager fcManager = (DefaultFilterChainManager) resolver.getFilterChainManager();
        NamedFilterList chain = fcManager.getChain("/users/**");
        assertNotNull(chain);
        assertEquals(chain.size(), 2);
        Filter[] filters = new Filter[chain.size()];
        filters = chain.toArray(filters);
        assertTrue(filters[1] instanceof TokenFilter);
    }

    @Test
    public void testCodeAuth() throws Exception {
        MvcResult result = mockMvc
                .perform(
                        get("/authentication").param("client_id", clientID).param("response_type", responseType)
                        .param("redirect_uri", redirectUri).param("state", "public")
                        .accept(MediaTypes.HAL_JSON)).andExpect(status().isFound()) // 302
                        .andReturn();
        String redirect = result.getResponse().getRedirectedUrl();
        assertTrue(redirect.matches(".*code=.*"));

        Pattern pattern = Pattern.compile(".*code=([^&]*)");
        Matcher matcher = pattern.matcher(redirect);
        String code = null;
        if (matcher.find()) {
            code = matcher.group(1);
        }

        result = mockMvc
                .perform(
                        post("/accessToken").param("code", code).param("client_id", clientID)
                        .param("client_secret", clientSecret).param("grant_type", grantType)
                        .param("redirect_uri", redirectUri).contentType("application/x-www-form-urlencoded")
                        .accept(MediaTypes.HAL_JSON)).andExpect(status().isOk()) // 200
                        .andExpect(jsonPath("$.expires_in").value(3600)).andReturn();

        String content = result.getResponse().getContentAsString();
        JSONObject json = new JSONObject(content);
        String accessToken = json.get("access_token").toString();
        mockMvc.perform(get("/users/1").header("Authorization", "Bearer " + accessToken).accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk()) // 200
        .andExpect(content().contentType(MediaTypes.HAL_JSON)) // 验证响应contentType
        .andReturn();
        mockMvc.perform(get("/users/1")).andExpect(status().isUnauthorized()) // 401
        .andReturn();
    }

    @Test
    public void testTokenAuth() throws Exception {
        responseType = ResponseType.TOKEN.toString();
        MvcResult result = mockMvc
                .perform(
                        get("/authentication").param("client_id", clientID).param("response_type", responseType)
                        .param("redirect_uri", redirectUri).param("state", "public")
                        .accept(MediaTypes.HAL_JSON)).andExpect(status().isFound()) // 302
                        .andReturn();
        String redirect = result.getResponse().getRedirectedUrl();
        assertTrue(redirect.matches(".*access_token=.*"));

        Pattern pattern = Pattern.compile(".*access_token=([^&]*)");
        Matcher matcher = pattern.matcher(redirect);
        String accessToken = null;
        if (matcher.find()) {
            accessToken = matcher.group(1);
        }

        mockMvc.perform(get("/users/1").header("Authorization", "Bearer " + accessToken).accept(MediaTypes.HAL_JSON))
        .andExpect(status().isOk()) // 200
        .andExpect(content().contentType(MediaTypes.HAL_JSON)) // 验证响应contentType
        .andReturn();
        mockMvc.perform(get("/users/1")).andExpect(status().isUnauthorized()) // 401
        .andReturn();
    }
}