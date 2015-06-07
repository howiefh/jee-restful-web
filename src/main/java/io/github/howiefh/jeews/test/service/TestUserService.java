package io.github.howiefh.jeews.test.service;

import java.util.List;

import io.github.howiefh.jeews.test.dao.TestUserDao;
import io.github.howiefh.jeews.test.entity.TestUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestUserService {
	@Autowired
    private TestUserDao userDao;
    
	public int insert(TestUser user) {
		return userDao.insert(user);
	}
    
	public TestUser getOne(long id) {
        return userDao.get(id);
	}
	public TestUser findByName(String username) {
		return userDao.findByName(username);
	}
    
	public List<TestUser> findAllList() {
		return userDao.findAllList();
	}
    
    public int update(TestUser user) {
		return userDao.update(user);
	}
    
    public int delete(TestUser user) {
		return userDao.delete(user);
	}
}