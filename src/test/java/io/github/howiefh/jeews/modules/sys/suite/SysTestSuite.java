/**
 * Copyright (c) 2015 https://github.com/howiefh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.suite;

import io.github.howiefh.jeews.modules.sys.controller.OrganizationControllerTest;
import io.github.howiefh.jeews.modules.sys.controller.RoleControllerTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 *
 *  @author howiefh
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    UserTestSuite.class,
    OrganizationControllerTest.class,
    RoleControllerTest.class
})
public class SysTestSuite {

}
