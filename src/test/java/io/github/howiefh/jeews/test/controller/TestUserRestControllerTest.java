package io.github.howiefh.jeews.test.controller;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import io.github.howiefh.jeews.common.shiro.ShiroTestUtils;
import io.github.howiefh.jeews.test.entity.TestUser;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.LinkDiscoverer;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.hal.CurieProvider;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; 

@RunWith(SpringJUnit4ClassRunner.class)
// 默认是src/main/webapp
@WebAppConfiguration(value = "src/main/webapp")
@TransactionConfiguration
@ContextHierarchy({
		@ContextConfiguration(name = "parent", locations = "classpath:config/spring-config.xml"),
		@ContextConfiguration(name = "child", locations = "classpath:config/spring-mvc.xml") })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserRestControllerTest {
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)  
		        .alwaysDo(print())  //默认每次执行请求后都做的动作  
		        .build();  
        TestUser user = new TestUser();
        user.setUsername("root");
        user.setLocked(false);
        ShiroTestUtils.mockCurrentUser(user, true);
	}

	@After
	public void tearDown() {
		ShiroTestUtils.clearSubject();
	}

	@Test
	public void testList() throws Exception {
		mockMvc.perform(get("/testusers").accept(MediaTypes.HAL_JSON))
	            .andExpect(status().isOk()) //200
	            .andExpect(content().contentType(MediaTypes.HAL_JSON)) //验证响应contentType  
				.andReturn();
	}
	@Test
	public void testGetTestUser() throws Exception {
		mockMvc.perform(get("/testusers/{id}",1L).accept(MediaTypes.HAL_JSON))
	            .andExpect(status().isOk()) //200
	            .andExpect(content().contentType(MediaTypes.HAL_JSON)) //验证响应contentType  
                .andExpect(header().doesNotExist("Etag"))
                .andExpect(jsonPath("$.rolesList[0]").value("admin"))
                .andExpect(jsonPath("$.username").value("root"))
				.andReturn();
	}

	@Test
	public void test3Delete() throws Exception {
        int id = 12;
	    mockMvc.perform(delete("/testusers/{id}", id)  
	            .contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaTypes.HAL_JSON)) //执行请求  
	            .andExpect(status().isNoContent()); //204
	      
	    mockMvc.perform(delete("/testusers/?id={id}", 0L)  
	            .accept(MediaType.APPLICATION_JSON)) //执行请求  
	            .andExpect(status().isInternalServerError()) //500错误请求  
	            .andExpect(jsonPath("$.code").value(500 + "")) //使用Json path验证JSON
	            .andReturn();  
	}

	@Test
	public void test2Update() throws Exception {
        int id = 12;
	    String requestBody = "{\"id\":"+id+",\"username\":\"user" + id + "\",\"password\":\"123456\",\"rolesList\":[\"user\"]}";  
	    mockMvc.perform(put("/testusers/{id}", id)  
	            .contentType(MediaType.APPLICATION_JSON).content(requestBody)  
	            .accept(MediaTypes.HAL_JSON)) //执行请求  
	            .andExpect(status().isOk()) //200
	            .andExpect(jsonPath("$.id").value(id)) //使用Json path验证JSON 请参考http://goessner.net/articles/JsonPath/  
	            .andExpect(content().contentType(MediaTypes.HAL_JSON)); //验证响应contentType  
	      
	    String errorBody = "{username:user2,password:123456,roles:[user]}";  
	    MvcResult result = mockMvc.perform(put("/testusers/{id}",id)  
	            .contentType(MediaType.APPLICATION_JSON).content(errorBody)  
	            .accept(MediaType.APPLICATION_JSON)) //执行请求  
	            .andExpect(status().isInternalServerError()) //500错误请求  
	            .andExpect(jsonPath("$.code").value(500 + "")) //使用Json path验证JSON
	            .andReturn();  
	      
	    assertTrue(HttpMessageNotReadableException.class.isAssignableFrom(result.getResolvedException().getClass()));//错误的请求内容体  
	}

	@Test
	public void test1Create() throws Exception {
        int id = 12;
	    String requestBody = "{\"id\":"+id+",\"username\":\"user"+id+"\",\"password\":\"123456\",\"rolesList\":[\"user, admin\"]}";  
	    mockMvc.perform(post("/testusers")  
	            .contentType(MediaType.APPLICATION_JSON).content(requestBody)  
	            .accept(MediaTypes.HAL_JSON)) //执行请求  
	            .andExpect(status().isCreated()) //201
	            .andExpect(jsonPath("$.id").value(id)) //使用Json path验证JSON
	            .andExpect(content().contentType(MediaTypes.HAL_JSON)); //验证响应contentType  
        
	    requestBody = "{\"username\":\"zhangsan\",\"password\":\"123456\",\"rolesList\":[\"user, admin\"]}";  
	    mockMvc.perform(post("/testusers")  
	            .contentType(MediaType.APPLICATION_JSON).content(requestBody)  
	            .accept(MediaTypes.HAL_JSON)) //执行请求  
	            .andExpect(status().isCreated()) //201
	            .andExpect(jsonPath("$.id").exists()) //使用Json path验证JSON
	            .andExpect(content().contentType(MediaTypes.HAL_JSON)); //验证响应contentType  
	}
    
	@Test
	public void enablesHyperMediaSupportFromXml() {
		assertThat(wac.getBean(RelProvider.class), is(notNullValue()));
		assertThat(wac.getBean(CurieProvider.class), is(notNullValue()));
		assertThat(wac.getBean(LinkDiscoverer.class), is(notNullValue()));
	}

}