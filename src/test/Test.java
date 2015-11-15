package test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wunmest.database.entity.User;

public class Test {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		SqlSessionFactory sessionFactory = (SqlSessionFactory) ctx.getBean("sqlSessionFactory");
		SqlSession session = sessionFactory.openSession();
		
		List<User> users = session.selectList("com.wunmest.database.dao.UserDao.select");
		for(User user : users){
			System.out.println(user);
		}
	}
}
