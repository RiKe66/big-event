package com.project.bigevent.service;

import com.project.bigevent.pojo.User;
import org.springframework.stereotype.Service;


public interface UserService {
    //查找用户名
    User findByUserName(String username);
    //注册
    void register(String username, String password);
}
