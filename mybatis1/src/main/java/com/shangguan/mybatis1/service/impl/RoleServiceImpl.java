package com.shangguan.mybatis1.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangguan.mybatis1.dao.RoleMapper;
import com.shangguan.mybatis1.entity.Role;
import com.shangguan.mybatis1.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleMapper roleMapper;

	public Map<Integer,String> getNameMap(){
		List<Role> lists = roleMapper.selectAll();
		Map<Integer,String> nameMap = new HashMap<>();
		if(lists == null || lists.size() < 1){
			return nameMap;
		}
		for(int  i = 0; i<lists.size();i++){
			Role role = lists.get(i);
			nameMap.put(role.getId(), role.getRolename());
		}
		return nameMap;
	}
	
}
