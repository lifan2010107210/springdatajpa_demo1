package com.kaikeba.dao;

import java.util.List;



















import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.kaikeba.entity.User;

/**
 * Repository接口的2个泛型:
 * 1. 表示的是要操作的实体类的类型
 * 2. 表示你的实体类的主键的类型
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User> {

	/**
	 * 根据username查询user对象
	 * @param username
	 * @return
	 */
	User getByUsername(String username);

	User getByUsernameAndPassword(String username, String password);

	List<User> getByUsernameLike(String username);
	
	
	
	/**
	 * 查询最大的id的user数据
	 *
	 */
	@Query("select u from User u where id=(select max(id) from User)")
	User getMaxUser();
	
	
	/**
	 * 根据password来查询user
	 */
	//@Query("from User where password=?")
	List<User> getUserByPassword(String password);
	
	
	/**
	 * 根据password来查询user
	 */
	@Query("from User where username=:xxx and password=:password")
	User getUserByUsernameAndPassword(
			@Param("xxx")String username, 
			@Param("password")String password);
	
	/**
	 * 根据id去更新user的username  增改
	 */
	@Modifying
	@Query("update User set username=? where id=?")
	@Transactional
	void updateUser(String username, int id);
	


	


}
