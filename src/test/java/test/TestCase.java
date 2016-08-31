package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.edu.hdu.chat.config.JpaConfig;
import cn.edu.hdu.chat.model.User;
import cn.edu.hdu.chat.repository.UserRepository;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestCase.class,JpaConfig.class})
@ComponentScan("cn.edu.hdu.chat.repository")
public  class TestCase{
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void test(){
		List<User > list = userRepository.findAll();
		System.out.println("password:"+list.get(0).getPassword());
	}
	
	@Test
	public void testFindByUsername(){
		User u = userRepository.findByUsername("zhangsan");
		System.out.println(JSON.toJSONString(u));
	}
	
}