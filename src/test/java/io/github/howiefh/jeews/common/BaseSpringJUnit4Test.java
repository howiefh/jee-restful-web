/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.common;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 *
 * @author howiefh
 */
// 默认是src/main/webapp
@WebAppConfiguration(value = "src/main/webapp")
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration
@ContextHierarchy({ @ContextConfiguration(name = "parent", locations = "classpath:config/spring-config.xml"),
        @ContextConfiguration(name = "child", locations = "classpath:config/spring-mvc.xml") })
public class BaseSpringJUnit4Test extends AbstractJUnit4SpringContextTests {

}
