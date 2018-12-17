package com.shangguan.mybatis.service;

import java.util.List;

import com.shangguan.mybatis.entity.User;

public interface UserService {

    public List<User> getAllUser();

    public void saveUser(User user);

    public void deleteUserById(Long id);

    public void updateUser(Long id, String userName, String passWord);
}