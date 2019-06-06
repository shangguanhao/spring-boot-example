package com.shangguan.mybatis1.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangguan.mybatis1.dao.UserRoleMapper;
import com.shangguan.mybatis1.entity.UserRole;
import com.shangguan.mybatis1.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	private UserRoleMapper userRoleMapper;
	
	@Override
	public void addUserRole(UserRole record) {
		userRoleMapper.insert(record);
	}

	@Override
	public void updateByPrimaryKey(Integer id, Integer userid, Integer roleid) {
		UserRole userRole = new UserRole();
		userRole.setId(id);
		userRole.setUserid(userid);
		userRole.setRoleid(roleid);
		userRoleMapper.updateByPrimaryKey(userRole);
	}

	@Override
	public UserRole selectByPrimaryKey(Integer id) {
		return userRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<UserRole> selectAll() {
		return userRoleMapper.selectAll();
	}

	@Override
	public List<UserRole> selectByUserId(Integer userid) {
		return userRoleMapper.selectByUserId(userid);
	}

	@Override
	public void deleteByPrimaryKey(Integer id) {
		userRoleMapper.deleteByPrimaryKey(id);
	}

}
