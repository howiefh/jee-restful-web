package io.github.howiefh.jeews.modules.sys.service;

import java.util.List;

import io.github.howiefh.jeews.modules.sys.dao.UserDao;
import io.github.howiefh.jeews.modules.sys.entity.User;

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
public class UserService {
	@Autowired
    private UserDao userDao;
    
	public int save(User user) {
        encryptPasswordAndGenSalt(user);
		return userDao.save(user);
	}
    
	public User findOne(long id) {
        return userDao.findOne(id);
	}
	public User findByName(String username) {
		return userDao.findByName(username);
	}
    
	public List<User> findAll() {
		return (List<User>) userDao.findAll();
	}
    
	public Page<User> findPageBy(Pageable pageable, User user) {
        List<User> users = (List<User>) userDao.findPageBy(pageable, user);
        long count = userDao.countBy(user);
        return new PageImpl<User>(users, pageable, count);
	}
    
    public int update(User user) {
        if (user.getPassword()!=null) {
            encryptPasswordAndGenSalt(user);
		}
		return userDao.update(user);
	}
    
    public int delete(Long id) {
		return userDao.delete(id);
	}
    public int deleteBatch(List<Long> ids) {
		return userDao.deleteBatch(ids);
	}
    private void encryptPasswordAndGenSalt(User user) {
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