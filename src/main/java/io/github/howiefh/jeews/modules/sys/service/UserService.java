package io.github.howiefh.jeews.modules.sys.service;

import io.github.howiefh.jeews.common.service.ServiceException;
import io.github.howiefh.jeews.modules.sys.dao.UserDao;
import io.github.howiefh.jeews.modules.sys.entity.User;
import io.github.howiefh.jeews.modules.sys.util.UserUtils;

import java.util.List;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final static String SEED = "abc";
    @Value("${shiro.password.hashAlgorithmName}")
    private String algorithmName = "md5";
    @Value("${shiro.password.hashIterations}")
    private int hashIterations = 2;

	@Autowired
    private UserDao userDao;

	public void save(User user) {
        encryptPasswordAndGenSalt(user);
        UserUtils.genCommonFiled(user);
        userDao.save(user);
        if (user.getRoles() != null && user.getRoles().size() > 0) {
            userDao.saveUserRole(user);
		} else {
			throw new ServiceException(user.getUsername() + "没有设置角色");
		}
        if (user.getOrganizations() != null && user.getOrganizations().size() > 0) {
            userDao.saveUserOrganization(user);
		}
	}

	public User findOne(long id) {
        return userDao.findOne(id);
	}
	public User findByName(String username) {
		return userDao.findByName(username);
	}

	public List<User> findAll() {
		return userDao.findAll();
	}

	public Page<User> findPageBy(Pageable pageable, User user) {
        List<User> users = userDao.findPageBy(pageable, user);
        long count = userDao.countBy(user);
        return new PageImpl<User>(users, pageable, count);
	}

    public void update(User user) {
        if (user.getPassword()!=null) {
            encryptPasswordAndGenSalt(user);
		}
        userDao.deleteUserRole(user);
        if (user.getRoles() != null && user.getRoles().size() > 0) {
            userDao.saveUserRole(user);
		} else {
			throw new ServiceException(user.getUsername() + "没有设置角色");
		}
        userDao.deleteUserOrganization(user);
        if (user.getOrganizations() != null && user.getOrganizations().size() > 0) {
            userDao.saveUserOrganization(user);
		}
		userDao.update(user);
	}

    public int delete(Long id) {
		return userDao.delete(id);
	}
    public int deleteBatch(List<Long> ids) {
		return userDao.deleteBatch(ids);
	}
    private void encryptPasswordAndGenSalt(User user) {
        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        generator.setSeed(SEED.getBytes());
        String salt = generator.nextBytes().toHex();
        user.setSalt(salt);

        DefaultHashService hashService = new DefaultHashService();
		HashRequest request = new HashRequest.Builder()
		            .setAlgorithmName(algorithmName).setSource(ByteSource.Util.bytes(user.getPassword()))
		            .setSalt(ByteSource.Util.bytes(salt)).setIterations(hashIterations).build();
        user.setPassword(hashService.computeHash(request).toHex());
	}

    public boolean passwordsMatch(User user, String password) {
        SecureRandomNumberGenerator generator = new SecureRandomNumberGenerator();
        generator.setSeed(SEED.getBytes());
        String salt = user.getSalt();

        DefaultHashService hashService = new DefaultHashService();
		HashRequest request = new HashRequest.Builder()
		            .setAlgorithmName(algorithmName).setSource(ByteSource.Util.bytes(password))
		            .setSalt(ByteSource.Util.bytes(salt)).setIterations(hashIterations).build();

        return user.getPassword().equals(hashService.computeHash(request).toHex());
    }

    public boolean isValidUser(String username, String password) {
		User user = findByName(username);
        if (user == null) {
			return false;
		}
        User checkedUser = new User();
        checkedUser.setUsername(username);
        checkedUser.setPassword(password);
        encryptPasswordAndGenSalt(checkedUser);
        return user.getPassword().equals(checkedUser.getPassword());
	}
}