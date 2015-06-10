package io.github.howiefh.jeews.test.service;

import java.util.List;

import io.github.howiefh.jeews.test.dao.TestUserDao;
import io.github.howiefh.jeews.test.entity.TestUser;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestUserService {
	@Autowired
    private TestUserDao userDao;
    
	public int insert(TestUser user) {
        encryptPasswordAndGenSalt(user);
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
        encryptPasswordAndGenSalt(user);
		return userDao.update(user);
	}
    
    public int delete(TestUser user) {
		return userDao.delete(user);
	}
    private void encryptPasswordAndGenSalt(TestUser user) {
        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        generator.setSeed("abc".getBytes());
        String salt = generator.nextBytes().toHex();
        user.setSalt(salt);
        
        DefaultHashService hashService = new DefaultHashService();
		HashRequest request = new HashRequest.Builder()  
		            .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes(user.getPassword()))  
		            .setSalt(ByteSource.Util.bytes(salt)).setIterations(2).build();  
        user.setPassword(hashService.computeHash(request).toHex());
	}
}