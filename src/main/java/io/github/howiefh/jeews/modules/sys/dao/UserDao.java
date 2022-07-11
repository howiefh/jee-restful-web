package io.github.howiefh.jeews.modules.sys.dao;

import org.springframework.stereotype.Repository;

import io.github.howiefh.jeews.common.dao.PagingAndSortingDao;
import io.github.howiefh.jeews.modules.sys.entity.User;

@Repository
public interface UserDao extends PagingAndSortingDao<User, Long> {
    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    User findByName(String username);

    /**
     * 删除用户角色关联数据
     *
     * @param user
     * @return
     */
    public int deleteUserRole(User user);

    /**
     * 插入用户角色关联数据
     *
     * @param user
     * @return
     */
    public int saveUserRole(User user);

    /**
     * 删除用户组织关联数据
     *
     * @param user
     * @return
     */
    public int deleteUserOrganization(User user);

    /**
     * 插入用户组织关联数据
     *
     * @param user
     * @return
     */
    public int saveUserOrganization(User user);
}