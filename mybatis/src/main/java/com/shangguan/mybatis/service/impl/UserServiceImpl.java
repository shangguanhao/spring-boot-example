package com.shangguan.mybatis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangguan.mybatis.entity.User;
import com.shangguan.mybatis.mapper.UserMapper;
import com.shangguan.mybatis.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getAllUser() {
        List<User> list = userMapper.getAll();
        return list;
    }

    @Override
    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }

    @Override
    public void updateUser(Long id, String userName, String passWord) {
        userMapper.updateUser(id, userName, passWord);
    }

}