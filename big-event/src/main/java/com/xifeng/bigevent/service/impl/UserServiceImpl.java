package com.xifeng.bigevent.service.impl;

import com.xifeng.bigevent.mapper.UserMapper;
import com.xifeng.bigevent.pojo.User;
import com.xifeng.bigevent.service.UserService;
import com.xifeng.bigevent.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;//通过mapper连接数据库操作
    @Override
    public User findByUserName(String username) {
        User u=userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5String= Md5Util.getMD5String(password);
        //添加
        userMapper.add(username,md5String);
    }
}
