package com.shangguan.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.shangguan.mybatis.entity.User;

@Mapper
public interface UserMapper {

    public List<User> getAll();

    public void saveUser(User user);

    public void deleteUserById(Long id);

    public void updateUser(@Param("id") Long id, @Param("userName") String userName,
            @Param("passWord") String passWord);

}