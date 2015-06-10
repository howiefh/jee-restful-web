package io.github.howiefh.jeews.test.controller;

import static org.junit.Assert.*;
import io.github.howiefh.jeews.common.shiro.ShiroTestUtils;
import io.github.howiefh.jeews.common.util.TestUtil;
import io.github.howiefh.jeews.test.entity.TestUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; 

@RunWith(SpringJUnit4ClassRunner.class)
// 默认是src/main/webapp
@WebAppConfiguration(value = "src/main/webapp")
@TransactionConfiguration
@ContextHierarchy({
		@ContextConfiguration(name = "parent", locations = "classpath:config/spring-config.xml"),
		@ContextConfiguration(name = "child", locations = "classpath:config/spring-mvc.xml") })
public class TestControllerTest {
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        TestUser user = new TestUser();
        user.setId(1L);
        user.setUsername("root");
        user.setLocked(false);
        ShiroTestUtils.mockCurrentUser(user, true);
	}

	@After
	public void tearDown() {
		ShiroTestUtils.clearSubject();
	}

	@Test
	public void testGetTestUser() throws Exception {
		mockMvc.perform(get("/test/{id}",1L))
	            .andExpect(status().isOk()) //200
	            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8)) //验证响应contentType  
                .andExpect(jsonPath("$.rolesList[0]").value("admin"))
                .andExpect(jsonPath("$.username").value("root"))
				.andDo(print()).andReturn();
	}

	@Test
	public void testDelete() throws Exception {
        int id = 12;
	    mockMvc.perform(delete("/test/{id}", id)  
	            .accept(MediaType.APPLICATION_JSON)) //执行请求  
	            .andExpect(status().isOk()) //200
	            .andExpect(jsonPath("$.msg").value("成功")) //使用Json path验证JSON
	            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8)); //验证响应contentType  
	      
	    mockMvc.perform(delete("/test/?id={id}", 0L)  
	            .accept(MediaType.APPLICATION_JSON)) //执行请求  
	            .andExpect(status().isMethodNotAllowed()) //405错误请求  
	            .andReturn();  
	}

	@Test
	public void testUpdate() throws Exception {
        long id = 12;
	    String requestBody = "{\"id\":"+id+",\"username\":\"user21\",\"password\":\"123456\",\"rolesList\":[\"user\"]}";  
	    mockMvc.perform(put("/test/{id}", id)  
	            .contentType(MediaType.APPLICATION_JSON).content(requestBody)  
	            .accept(MediaType.APPLICATION_JSON)) //执行请求  
	            .andExpect(status().isOk()) //200
	            .andExpect(jsonPath("$.msg").value("成功")) //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/  
	            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8)); //验证响应contentType  
	      
	    String errorBody = "{username:user2,password:123456,roles:[user]}";  
	    MvcResult result = mockMvc.perform(put("/test/{id}",id)  
	            .contentType(MediaType.APPLICATION_JSON).content(errorBody)  
	            .accept(MediaType.APPLICATION_JSON)) //执行请求  
	            .andExpect(status().isBadRequest()) //400错误请求  
	            .andReturn();  
	      
	    assertTrue(HttpMessageNotReadableException.class.isAssignableFrom(result.getResolvedException().getClass()));//错误的请求内容体  
	}

	@Test
	public void testCreate() throws Exception {
        int id = 12;
	    String requestBody = "{\"id\":"+id+",\"username\":\"user83\",\"password\":\"123456\",\"rolesList\":[\"user,admin\"]}";  
	    mockMvc.perform(post("/test")  
	            .contentType(MediaType.APPLICATION_JSON).content(requestBody)  
	            .accept(MediaType.APPLICATION_JSON)) //执行请求  
	            .andExpect(status().isOk()) //200
	            .andExpect(jsonPath("$.msg").value("成功")) //使用Json path验证JSON
	            .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8)); //验证响应contentType  
	      
	    String errorBody = "{username:user2,password:123456,roles:[user]}";  
	    MvcResult result = mockMvc.perform(post("/test")  
	            .contentType(MediaType.APPLICATION_JSON).content(errorBody)  
	            .accept(MediaType.APPLICATION_JSON)) //执行请求  
	            .andExpect(status().isBadRequest()) //400错误请求  
	            .andReturn();  
	      
	    assertTrue(HttpMessageNotReadableException.class.isAssignableFrom(result.getResolvedException().getClass()));//错误的请求内容体  
	}

}