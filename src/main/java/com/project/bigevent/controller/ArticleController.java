package com.project.bigevent.controller;

import com.project.bigevent.pojo.Result;
import com.project.bigevent.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @RequestMapping("/list")
    public Result<String> list(@RequestHeader(name = "Authorization") String token,HttpServletResponse response){
        //验证token
        try {
            Map<String,Object> claims = JwtUtil.parseToken(token);
            return Result.success("成功");
        } catch (Exception e) {
            response.setStatus(401);
            return Result.error("未登录");
        }

    }
}
