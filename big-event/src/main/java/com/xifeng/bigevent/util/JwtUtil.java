package com.xifeng.bigevent.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

//JWT令牌工具类
public class JwtUtil {

    private static final String KEY="itheima";//设置密钥

    //接收业务数据，生成token并返回
    public static String genToken(Map<String,Object> claims){
        return JWT.create()
                .withClaim("claims",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//设置过期时间，12h
                .sign(Algorithm.HMAC256(KEY));//指定算法，配置密钥KEY
    }

    //接收token，验证token，并返回业务数据，Map集合数据类型
    public static Map<String,Object> parseToken(String token){
        return JWT.require(Algorithm.HMAC256(KEY))//require()申请JWT验证器，算法、密钥与上面生成JWT的保持一致，密钥KEY
                .build()//build()生成一个验证器
                .verify(token)//验证token，生成一个解析后的JWT对象
                .getClaim("claims")//得到所有的载荷
                .asMap();
    }
}
