package com.shangguan.mybatis1.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangguan.mybatis1.dao.UserMapper;
import com.shangguan.mybatis1.dao.UserRoleMapper;
import com.shangguan.mybatis1.entity.User;
import com.shangguan.mybatis1.entity.UserDetail;
import com.shangguan.mybatis1.entity.UserRole;
import com.shangguan.mybatis1.service.RoleService;
import com.shangguan.mybatis1.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired 
	private UserRoleMapper userRoleMapper;
	
	@Autowired
	private RoleService roleService;
	
	public List<UserDetail> selectAll(){
		List<User> userList = new ArrayList<>();
		List<UserDetail> details = new ArrayList<>();
		
		try{
			userList = userMapper.selectAll();
			details = getUserDetails(userList);
		}catch(Exception e){
			e.printStackTrace();
			return details;
		}
		return details;
	}
	
	public  List<UserDetail> getUserDetails(List<User> lists){
		List<UserDetail> details = new ArrayList<>();
		if(lists == null || lists.size() < 1){
			return details;
		}
		Map<Integer, String> nameMap = roleService.getNameMap();
		for(int i=0; i< lists.size();i++){
			User user = lists.get(i);
			UserDetail detail = new UserDetail();

			detail.setUserid(user.getId());
			detail.setUsername(user.getUsername());
			detail.setPassword(user.getPassword());
			List<Integer> roleids = new ArrayList<>();
			List<String> rolenames = new ArrayList<>();
			List<UserRole> userroles = userRoleMapper.selectByUserId(user.getId());
			for(int j=0;j<userroles.size();j++) {
				roleids.add(userroles.get(j).getRoleid());
				rolenames.add(nameMap.get(userroles.get(j).getRoleid()));
			}
			detail.setRoleids(roleids);
			detail.setRolenames(rolenames);
			details.add(detail);
		}
		return details;
	}

	@Override
	public void addUser(User user) {
		userMapper.insert(user);
	}

	@Override
	public void deleteUserById(Integer id) {
		userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void updateUser(Integer id, String username, String password) {
		userMapper.updateByPrimaryKey(id,username,password);
	}
}
