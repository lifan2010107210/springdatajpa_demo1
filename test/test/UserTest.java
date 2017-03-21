package test;
import java.util.List;







import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

import com.kaikeba.dao.UserDao;
import com.kaikeba.entity.User;

public class UserTest {
	@Test
	public void testGetUser(){
		//1. 加载配置文件
		ApplicationContext context = 
				new ClassPathXmlApplicationContext("applicationContext.xml");
		
		//2. 获取UserDao的bean
		//spring data jpa会自动把dao层的接口包装成一个代理对象, 并且作为spring bean
		UserDao dao = context.getBean(UserDao.class);
		System.out.println(dao.getClass().getName());
		
		//3. 操作UserDao
		User user = dao.getByUsername("abc");
		System.out.println(user);
	}
	
	ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	UserDao dao = context.getBean(UserDao.class);
	
	
	@Test
	public void testGetUser2(){
		//3. 操作UserDao
		User user = dao.getByUsernameAndPassword("abc","123");
		System.out.println(user);
	}
	
	@Test
	public void testGetUser3(){
		//3. 操作UserDao
		List<User> list =  dao.getByUsernameLike("%a%");
		System.out.println(list);
	}
	

	@Test
	public void testGetMaxUser(){
		User user = dao.getMaxUser();
		System.out.println(user);
	}
	@Test
	public void testGetUserBYPassword(){
		List<User> list = dao.getUserByPassword("123");
		System.out.println(list);
	}
	
	
	@Test
	public void testUpdateUser(){
		dao.updateUser("edf", 1);
	}
	
	//save  分页    直接写就行了    不用写dao 
	@Test
	public void testSave(){
		User user = new User();
		user.setUsername("qqq");
		user.setPassword("456");
		dao.save(user);
	}
	
	@Test
	public void testPage(){
		int currentPage = 0;//从0开始
		int pageSize = 2;
		PageRequest pageRequest = new PageRequest(currentPage, pageSize);
		//findAll在父接口中定义的方法
		Page<User> page = dao.findAll(pageRequest);
		System.out.println("总记录数:"+page.getTotalElements());
		System.out.println("当前第几页:"+page.getNumber());
		System.out.println("总页数:"+page.getTotalPages());
		System.out.println("结果:"+page.getContent());
	}
	
	
	@Test
	public void testPage1(){
		int currentPage = 1;//从0开始
		int pageSize = 3;
		//根据username排序, 顺序
		Order order = new Order(Direction.ASC, "username");
		
		Sort sort = new Sort(order);
		PageRequest pageRequest = new PageRequest(currentPage, pageSize, sort);
		//findAll在父接口中定义的方法
		Specification<User> spec = new Specification<User>() {
			//查询id>3的user数据, 分页
			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				Path path = root.get("id");//取到实体类的id属性
				//生成一个条件 id > 3
				Predicate predicate = cb.gt(path, 3);
				return predicate;
			}
		};
		
		Page<User> page = dao.findAll(spec, pageRequest);
		System.out.println("总记录数:"+page.getTotalElements());
		System.out.println("当前第几页:"+page.getNumber());
		System.out.println("总页数:"+page.getTotalPages());
		System.out.println("结果:"+page.getContent());
		System.out.println(page.getSize());
		System.out.println(page.getNumberOfElements());
	}
	
	
	
	
}
