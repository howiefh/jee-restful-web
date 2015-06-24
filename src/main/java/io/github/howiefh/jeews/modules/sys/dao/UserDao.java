package io.github.howiefh.jeews.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import io.github.howiefh.jeews.common.dao.PagingAndSortingDao;
import io.github.howiefh.jeews.modules.sys.entity.User;

@Repository
public interface UserDao extends PagingAndSortingDao<User, Long>{
	User findByName(String username);
	int deleteBatch(@Param("ids") List<Long> ids);
}