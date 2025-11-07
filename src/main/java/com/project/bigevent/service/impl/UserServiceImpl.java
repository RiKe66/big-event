package com.project.bigevent.service.impl;

import com.project.bigevent.mapper.UserMapper;
import com.project.bigevent.pojo.User;
import com.project.bigevent.service.UserService;
import com.project.bigevent.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        User u = userMapper.selectByUsername(username);
        return u;
    }
    //My-batis Plus自带注册（添加用户）
    @Override
    public void register(String username, String password) {
        //加密处理
        String md5String= Md5Util.getMD5String(password);
        //添加
        User user = new User();
        user.setUsername(username);
        user.setPassword(md5String);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);

    }

    @Override
    public void update(User user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }
}
