package com.shangguan.mybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shangguan.mybatis.entity.User;
import com.shangguan.mybatis.service.UserService;

@RestController
@SpringBootApplication
@RequestMapping("/mybatis")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getAllUser")
    public List<User> getAllUser() {
        List<User> list = userService.getAllUser();
        return list;
    }

    @RequestMapping("/saveUser")
    public void saveUser(User user) {
        userService.saveUser(user);
    }

    @RequestMapping("/deleteUserById")
    public void deleteUserById(Long id) {
        userService.deleteUserById(id);
    }

    @RequestMapping("/updateUser")
    public void updateUser(Long id, String userName, String passWord) {
        userService.updateUser(id, userName, passWord);
    }

}
