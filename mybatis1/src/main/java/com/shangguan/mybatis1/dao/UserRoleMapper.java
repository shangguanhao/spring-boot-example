package com.shangguan.mybatis1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.shangguan.mybatis1.entity.UserRole;

@Mapper
public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    List<UserRole> selectAll();
    
    List<UserRole> selectByUserId(Integer userid);

    int updateByPrimaryKey(UserRole record);
}