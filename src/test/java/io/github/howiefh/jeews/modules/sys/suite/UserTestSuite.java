/**
 * Copyright (c) 2015 https://github.com/howiefh
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package io.github.howiefh.jeews.modules.sys.suite;

import io.github.howiefh.jeews.modules.sys.controller.LoginControllerTest;
import io.github.howiefh.jeews.modules.sys.controller.SignupControllerTest;
import io.github.howiefh.jeews.modules.sys.controller.UserControllerTest;
import io.github.howiefh.jeews.modules.sys.dao.UserDaoTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 *
 * @author howiefh
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({UserDaoTest.class, UserControllerTest.class, LoginControllerTest.class,
        SignupControllerTest.class})
public class UserTestSuite {

}
