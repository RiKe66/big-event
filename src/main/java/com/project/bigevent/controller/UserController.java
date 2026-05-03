package com.project.bigevent.controller;


import com.mysql.cj.util.StringUtils;
import com.project.bigevent.pojo.Result;
import com.project.bigevent.pojo.User;
import com.project.bigevent.service.UserService;
import com.project.bigevent.utils.JwtUtil;
import com.project.bigevent.utils.Md5Util;
import com.project.bigevent.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
            //生成token
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginuser.getId());
            claims.put("username",loginuser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }

        return Result.error("密码错误");

    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
        //根据用户名查询用户
      /*  Map<String,Object>  map = JwtUtil.parseToken(token);
        String username = (String) map.get("username");*/
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }


    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();

    }

    @PatchMapping("updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params){
        //1.校验参数
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if(StringUtils.isNullOrEmpty(oldPwd)||StringUtils.isNullOrEmpty(newPwd)||StringUtils.isNullOrEmpty(rePwd)){
            return Result.error("缺少参数");
        }

        //原密码是否正确
        //调用userservice根据用户名拿到原密码
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String)map.get("username");
        User loginUser = userService.findByUserName(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码错误");
        }

        if (!rePwd.equals(newPwd)){
            return Result.error("两次密码不一致");
        }

        //2.调用service完成更新
        userService.updatePwd(newPwd);
        return Result.success();


    }


}
