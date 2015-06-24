package io.github.howiefh.jeews.test.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import io.github.howiefh.jeews.common.dao.PagingAndSortingDao;
import io.github.howiefh.jeews.test.entity.TestUser;

@Repository
public interface TestUserDao extends PagingAndSortingDao<TestUser, Long>{
	TestUser findByName(String username);
	int deleteBatch(@Param("ids") List<Long> ids);
}
