package com.project.bigevent.service;

import com.project.bigevent.pojo.User;
import org.springframework.stereotype.Service;


public interface UserService {
    //查找用户名
    User findByUserName(String username);
    //注册（mybatisplus自带的默认添加用户的方法）
    void register(String username, String password);
    //更新（手写sql语句）
    void update(User user);
    //更新 mybatisplus自带方法

}
