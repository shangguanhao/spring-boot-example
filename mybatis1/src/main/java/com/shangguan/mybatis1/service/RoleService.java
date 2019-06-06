package com.shangguan.mybatis1.service;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public interface RoleService {

	public Map<Integer,String> getNameMap();
	
}
