package com.xifeng.bigevent.controller;

import com.xifeng.bigevent.pojo.Result;
import com.xifeng.bigevent.pojo.User;
import com.xifeng.bigevent.service.UserService;
import com.xifeng.bigevent.util.JwtUtil;
import com.xifeng.bigevent.util.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
//注册
    @PostMapping("/register")//注册接口用post，路径/user/register
    public Result register(@Pattern(regexp = "^\\S{5,16}$") @RequestParam String username, @Pattern(regexp = "^\\S{5,16}$") @RequestParam String password){
        //@Validated，@Pattern正则表达式自动参数校验
        //参数校验可if()else简单判断，但当参数过多时会异常繁杂
        //使用Spring Validation，对注册接口的参数进行合法性校验
        //查询用户
        User u=userService.findByUserName(username);
        if(u==null){
            //没有占用
            //注册
            userService.register(username,password);
            return Result.success();
        }else {
            //占用
            return Result.error("用户名已被占用");
        }
    }

    //登录
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") @RequestParam String username, @Pattern(regexp = "^\\S{5,16}$") @RequestParam String password){
        //根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        if(loginUser == null){
            return Result.error("用户名不存在");
        }
        //判断密码是否正确（数据库中存的是MD5加密后的密码）
        if(loginUser.getPassword().equals(Md5Util.getMD5String(password))){
            //登陆成功
            //把id，username塞入Map集合claims中，，生成JWT令牌token，输出令牌
            Map<String, Object> claims=new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }else{
            //登陆失败
            return Result.error("密码错误");
        }
    }
}
