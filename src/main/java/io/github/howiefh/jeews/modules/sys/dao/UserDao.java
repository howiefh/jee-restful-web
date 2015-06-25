package io.github.howiefh.jeews.modules.sys.dao;

import org.springframework.stereotype.Repository;

import io.github.howiefh.jeews.common.dao.PagingAndSortingDao;
import io.github.howiefh.jeews.modules.sys.entity.User;

@Repository
public interface UserDao extends PagingAndSortingDao<User, Long>{
	User findByName(String username);
}