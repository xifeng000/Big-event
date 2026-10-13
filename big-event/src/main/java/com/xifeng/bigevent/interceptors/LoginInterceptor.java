package com.xifeng.bigevent.interceptors;

import com.xifeng.bigevent.pojo.Result;
import com.xifeng.bigevent.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

//使用拦截器统一验证令牌
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        //验证token
        try {
            Map<String,Object> claims= JwtUtil.parseToken(token);
            //验证成功，放行
            return true;
        } catch (Exception e) {
            //http响应状态码为401
            response.setStatus(401);
            //失败，不放行
            return false;
        }
    }
}
