package com.shangguan.mybatis1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shangguan.mybatis1.entity.User;
import com.shangguan.mybatis1.entity.UserDetail;

@Service
public interface UserService {
	
	List<UserDetail> selectAll();

	void addUser(User user);

	void deleteUserById(Integer id);

	void updateUser(Integer id, String username, String password);
	
}
