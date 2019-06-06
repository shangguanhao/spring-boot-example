package com.shangguan.mybatis1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shangguan.mybatis1.entity.Role;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);
}