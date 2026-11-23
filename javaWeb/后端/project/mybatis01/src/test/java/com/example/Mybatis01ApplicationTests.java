package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.mapper.UserMapper;
import java.util.List;
import com.example.pojo.User;

@SpringBootTest
class Mybatis01ApplicationTests {

	@Autowired 
	private UserMapper userMapper;

	@Test
	public void testFindAll() {
		List<User> userList = userMapper.findAll();
		userList.forEach(user->System.out.println(user));
		
	}

}
