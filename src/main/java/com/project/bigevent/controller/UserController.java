package com.project.bigevent.controller;


import com.project.bigevent.pojo.Result;
import com.project.bigevent.pojo.User;
import com.project.bigevent.service.UserService;
import com.project.bigevent.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{2,16}$") String username, @Pattern(regexp = "^\\S{4,16}$")String password) {
        //查询用户
        User u = userService.findByUserName(username);
        if(u == null){
            //没有占用，需要注册
            userService.register(username,password);
            return Result.success();
        }else {
            //被占用
            return Result.error("用户名已被占用");
        }
    }

    @PostMapping("/login")
    public  Result<String> login(@Pattern(regexp = "^\\S{2,16}$") String username, @Pattern(regexp = "^\\S{4,16}$")String password) {
        //根据用户名查询用户
        User loginuser = userService.findByUserName(username);
        //判断用户是否存在
        if (loginuser == null) {
            return  Result.error("用户名不存在");
        }
        //判断密码是否正确
        if(Md5Util.getMD5String(password).equals(loginuser.getPassword())){
            //登录成功
            return Result.success("dwt token");
        }

        return Result.error("密码错误");

    }



}
