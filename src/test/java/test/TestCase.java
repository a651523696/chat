package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.hdu.chat.config.JpaConfiguration;
import cn.edu.hdu.chat.model.User;
import cn.edu.hdu.chat.repository.UserRepository;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={TestCase.class,JpaConfiguration.class})
@ComponentScan("cn.edu.hdu.chat.repository")
public  class TestCase{
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void test(){
		List<User > list = userRepository.findAll();
		System.out.println("password:"+list.get(0).getPassword());
	}
	
}