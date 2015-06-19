package io.github.howiefh.jeews.test.service;

import java.util.List;

import io.github.howiefh.jeews.test.dao.TestUserDao;
import io.github.howiefh.jeews.test.entity.TestUser;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TestUserService {
	@Autowired
    private TestUserDao userDao;
    
	public int save(TestUser user) {
        encryptPasswordAndGenSalt(user);
		return userDao.save(user);
	}
    
	public TestUser findOne(long id) {
        return userDao.findOne(id);
	}
	public TestUser findByName(String username) {
		return userDao.findByName(username);
	}
    
	public List<TestUser> findAll() {
		return (List<TestUser>) userDao.findAll();
	}
    
	public Page<TestUser> findAllByPage(Pageable pageable) {
        List<TestUser> users = (List<TestUser>) userDao.findAllByPage(pageable);
        long count = userDao.count();
        return new PageImpl<TestUser>(users, pageable, count);
	}
    
    public int update(TestUser user) {
        if (user.getPassword()!=null) {
            encryptPasswordAndGenSalt(user);
		}
		return userDao.update(user);
	}
    
    public int delete(Long id) {
		return userDao.delete(id);
	}
    public int deleteAll(List<Long> ids) {
		return userDao.deleteAll(ids);
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