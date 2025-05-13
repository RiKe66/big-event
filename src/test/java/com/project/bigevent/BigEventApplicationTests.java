package com.project.bigevent;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BigEventApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public  void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("name","zhangsan");
        //生成jwt代码
        String token = JWT.create()
                .withClaim("user",claims)//添加载何
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60))//添加过期时间
                .sign(Algorithm.HMAC256("RiKe"));//制定算法，配置密钥
        System.out.println(token);
    }

    @Test
    public void testParse(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7Im5hbWUiOiJ6aGFuZ3NhbiIsImlkIjoxfSwiZXhwIjoxNzQ3MTQ1NTIwfQ." +
                "CFRrEtteuuY_OHmL1D1qBkwAegsAOmEZPsvdPC5-gTM";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("RiKe")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
    }

}
