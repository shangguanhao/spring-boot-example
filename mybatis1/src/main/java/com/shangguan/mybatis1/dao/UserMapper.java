package com.shangguan.mybatis1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shangguan.mybatis1.entity.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    void updateByPrimaryKey(@Param("id")Integer id, @Param("username")String username, @Param("password")String password);
}