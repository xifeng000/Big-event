package com.xifeng.bigevent;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    //JWT令牌生成
    @Test//单元测试注解
    public void testGen(){
        Map<String, Object> claims=new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");

        //生成jwt的代码
        String token=JWT.create()
                .withClaim("user",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//设置过期时间，12h
                //System.currentTimeMillis()系统的当前时间毫秒值
                .sign(Algorithm.HMAC256("itheima"));//指定算法，配置密钥itheima
        //JWT令牌组成：Header(头),记录令牌类型与签名算法
        //PayLoad（载荷），携带自定义信息，不能存放加密信息（公开的）
        //Signature（签名），对头部和载荷进行加密计算得来
        System.out.println(token);
    }

    //JWT验证
    @Test
    public void testParse(){
        //定义字符串，模拟用户传递过来的token
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3OTE4ODY0NDB9" +
                ".adXMHVhCG2JxHehvF1OLKdyuQsW-rzTkhSYkONa6PT0";

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("itheima")).build();//密钥为itheima
        //require()申请JWT验证器，算法、密钥与上面生成JWT的保持一致，build()生成一个验证器
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        //验证token，生成一个解析后的JWT对象
        Map<String, Claim> claims = decodedJWT.getClaims();
        //得到所有的载荷
        System.out.println(claims.get("user"));

        //如果篡改了头部或载荷部分的数据，验证失败
        //如果密钥更改了，验证失败
        //token过期也失败
    }

}
