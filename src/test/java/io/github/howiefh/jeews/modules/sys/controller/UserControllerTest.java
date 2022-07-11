package io.github.howiefh.jeews.modules.sys.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.howiefh.jeews.common.BaseSpringJUnit4Test;
import io.github.howiefh.jeews.common.shiro.ShiroTestUtils;
import io.github.howiefh.jeews.modules.sys.entity.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.LinkDiscoverer;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest extends BaseSpringJUnit4Test {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private static int id;
    private static List<Integer> ids = new ArrayList<Integer>();

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).alwaysDo(print()) // 默认每次执行请求后都做的动作
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
        mockMvc.perform(
                        get("/users?page=0&size=10&sort=id,asc&sort=username,desc&username=user").accept(MediaTypes.HAL_JSON)) // 执行请求
                .andExpect(status().isOk()) // 200
                .andExpect(content().contentType(MediaTypes.HAL_JSON)); // 验证响应contentType
        mockMvc.perform(
                        get("/users?page=0&size=10&sort=id,asc&sort=username,desc&email=10").accept(MediaTypes.HAL_JSON)) // 执行请求
                .andExpect(status().isOk()) // 200
                .andExpect(content().contentType(MediaTypes.HAL_JSON)); // 验证响应contentType
        mockMvc.perform(get("/users?page=0&size=10&sort=id,asc").accept(MediaTypes.HAL_JSON)) // 执行请求
                .andExpect(status().isOk()) // 200
                .andExpect(content().contentType(MediaTypes.HAL_JSON));
        mockMvc.perform(get("/users").accept(MediaTypes.HAL_JSON)) // 执行请求
                .andExpect(status().isOk()) // 200
                .andExpect(content().contentType(MediaTypes.HAL_JSON));
    }

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/users/{id}", 1L).accept(MediaTypes.HAL_JSON)).andExpect(status().isOk()) // 200
                .andExpect(content().contentType(MediaTypes.HAL_JSON)) // 验证响应contentType
                .andExpect(header().doesNotExist("Etag")).andExpect(jsonPath("$.username").value("root")).andReturn();
    }

    @Test
    public void test1Create() throws Exception {
        String requestBody = "{\"username\":\"fh" + UUID.randomUUID() + "\",\"password\":\"123456\",\"email\":\""
                + UUID.randomUUID() + "@qq.om\",\"mobile\":" + "\"" + new Random().nextInt()
                + "\",\"roles\":[1,2],\"organizations\":[1],\"locked\":true}";
        MvcResult result = mockMvc
                .perform(
                        post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody)
                                .accept(MediaTypes.HAL_JSON)) // 执行请求
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.id").exists()) // 使用Json path验证JSON
                .andExpect(content().contentType(MediaTypes.HAL_JSON)).andReturn(); // 验证响应contentType
        String location = result.getResponse().getHeader("Location");
        id = getId(location);
    }

    @Test
    public void test2Update() throws Exception {
        String requestBody = "{\"id\":" + id + ",\"username\":\"user" + id + "\",\"roles\":[1]}";
        // requestBody = "{\"id\":"+id+",\"username\":\"user" + id +
        // "\",\"password\":\"123456\",\"roles\":[1]}";
        mockMvc.perform(
                        put("/users/{id}", id).contentType(MediaType.APPLICATION_JSON).content(requestBody)
                                .accept(MediaTypes.HAL_JSON)) // 执行请求
                .andExpect(status().isOk()) // 200
                .andExpect(jsonPath("$.id").value(id)) // 使用Json path验证JSON
                // 请参考http://goessner.net/articles/JsonPath/
                .andExpect(content().contentType(MediaTypes.HAL_JSON)); // 验证响应contentType

        String errorBody = "{username:user2,password:123456,roles:[1]}";
        MvcResult result = mockMvc
                .perform(
                        put("/users/{id}", id).contentType(MediaType.APPLICATION_JSON).content(errorBody)
                                .accept(MediaType.APPLICATION_JSON)) // 执行请求
                .andExpect(status().isInternalServerError()) // 500错误请求
                .andExpect(jsonPath("$.code").value(500 + "")) // 使用Json
                // path验证JSON
                .andReturn();

        assertTrue(HttpMessageNotReadableException.class.isAssignableFrom(result.getResolvedException().getClass()));// 错误的请求内容体
    }

    @Test
    public void test3Delete() throws Exception {
        mockMvc.perform(delete("/users/{id}", id).contentType(MediaType.APPLICATION_JSON).accept(MediaTypes.HAL_JSON)) // 执行请求
                .andExpect(status().isNoContent()); // 204

        mockMvc.perform(delete("/users/?id={id}", 0L).accept(MediaType.APPLICATION_JSON)) // 执行请求
                .andExpect(status().isInternalServerError()) // 500错误请求
                .andExpect(jsonPath("$.code").value(500 + "")) // 使用Json
                // path验证JSON
                .andReturn();
    }

    @Test
    public void test4DeleteAll() throws Exception {
        for (int i = 0; i < 3; i++) {
            String requestBody = "{\"username\":\"u" + UUID.randomUUID()
                    + "\",\"password\":\"123456\",\"roles\":[1, 2],\"locked\":true}";
            MvcResult result = mockMvc
                    .perform(
                            post("/users").contentType(MediaType.APPLICATION_JSON).content(requestBody)
                                    .accept(MediaTypes.HAL_JSON)) // 执行请求
                    .andExpect(status().isCreated()) // 201
                    .andReturn();
            String location = result.getResponse().getHeader("Location");
            id = getId(location);
            ids.add(id);
        }
        mockMvc.perform(
                        delete("/users").contentType(MediaType.APPLICATION_JSON).content(ids.toString())
                                .accept(MediaTypes.HAL_JSON)) // 执行请求
                .andExpect(status().isNoContent()); // 204
    }

    @Test
    public void enablesHyperMediaSupportFromXml() {
        assertThat(wac.getBean(RelProvider.class), is(notNullValue()));
        assertThat(wac.getBean(CurieProvider.class), is(notNullValue()));
        assertThat(wac.getBean(LinkDiscoverer.class), is(notNullValue()));
        ObjectMapper objectMapper = (ObjectMapper) wac.getBean("_halObjectMapper");
        assertThat(objectMapper, is(notNullValue()));
        assertTrue(objectMapper.getDateFormat().equals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
    }

    private int getId(String location) {
        int index = location.lastIndexOf('/');
        if (index != -1) {
            return Integer.valueOf(location.substring(index + 1));
        }
        return -1;
    }
}