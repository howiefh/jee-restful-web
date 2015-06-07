package io.github.howiefh.jeews.test.dao;

import org.springframework.stereotype.Repository;

import io.github.howiefh.jeews.test.dao.api.TestBaseDao;
import io.github.howiefh.jeews.test.entity.TestUser;

@Repository
public interface TestUserDao extends TestBaseDao<TestUser, Long>{
	TestUser findByName(String username);
}
