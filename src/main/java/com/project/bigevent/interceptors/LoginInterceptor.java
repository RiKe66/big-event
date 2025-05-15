package com.project.bigevent.interceptors;

import com.project.bigevent.pojo.Result;
import com.project.bigevent.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
            String token = request.getHeader("Authorization");
        //验证token
        try {
            Map<String,Object> claims = JwtUtil.parseToken(token);
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }
}
