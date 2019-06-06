package com.shangguan.mybatis1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shangguan.mybatis1.entity.UserRole;

@Service
public interface UserRoleService {

	void addUserRole(UserRole record);
	
	void deleteByPrimaryKey(Integer id);
	
	void updateByPrimaryKey(Integer id, Integer userid, Integer roleid);
	
	UserRole selectByPrimaryKey(Integer id);
	
	List<UserRole> selectAll();
	
	List<UserRole> selectByUserId(Integer userid);
}
