package com.shangguan.mybatis1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shangguan.mybatis1.entity.User;
import com.shangguan.mybatis1.entity.UserDetail;
import com.shangguan.mybatis1.entity.UserRole;
import com.shangguan.mybatis1.result.Result;
import com.shangguan.mybatis1.result.ResultUtils;
import com.shangguan.mybatis1.result.States;
import com.shangguan.mybatis1.service.UserRoleService;
import com.shangguan.mybatis1.service.UserService;

@RestController
@RequestMapping("/mybatis1")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @RequestMapping("/getAllUser")
    public Result getAllUser() {
        List<UserDetail> list = userService.selectAll();
        return ResultUtils.result(States.errorCode.SUCCESS, "查询成功", list);
    }

    @RequestMapping("/addUser")
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public Result saveUser(@RequestParam(value = "username") String username, 
    		@RequestParam(value = "password") String password, 
    		@RequestParam(value = "roleids") List<Integer> roleids) {
    	User user = new User();
    	user.setUsername(username);
    	user.setPassword(password);
        userService.addUser(user);
        for(int i=0;i<roleids.size();i++) {
        	UserRole userRole = new UserRole();
        	userRole.setUserid(user.getId());
        	userRole.setRoleid(roleids.get(i));
        	userRoleService.addUserRole(userRole);
        }
        return ResultUtils.result(States.errorCode.SUCCESS, "添加成功", null);
    }

    @RequestMapping("/deleteUserById")
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public Result deleteUserById(Integer id) {
        userService.deleteUserById(id);
        List<UserRole> list = userRoleService.selectByUserId(id);
        for(int i=0;i<list.size();i++) {
        	userRoleService.deleteByPrimaryKey(list.get(i).getId());
        }
        return ResultUtils.result(States.errorCode.SUCCESS, "删除成功", null);
    }

    @RequestMapping("/updateUser")
    @Transactional(rollbackFor={RuntimeException.class, Exception.class})
    public Result updateUser(@RequestParam(value = "id")Integer id, 
    		@RequestParam(value = "username") String username, 
    		@RequestParam(value = "password") String password, 
    		@RequestParam(value = "roleids") List<Integer> roleids) {
        userService.updateUser(id, username, password);
        //查找user_role然后按照id进行删除
        List<UserRole> list = userRoleService.selectByUserId(id);
        for(int i=0;i<list.size();i++) {
        	userRoleService.deleteByPrimaryKey(list.get(i).getId());
        }
        //插入新的roleids
        for(int i=0;i<roleids.size();i++) {
        	UserRole record = new UserRole();
        	record.setUserid(id);
        	record.setRoleid(roleids.get(i));
        	userRoleService.addUserRole(record);
        }
        return ResultUtils.result(States.errorCode.SUCCESS, "更新成功", null);
    }

}
