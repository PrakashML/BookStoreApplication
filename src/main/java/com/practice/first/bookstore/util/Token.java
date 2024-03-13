package com.practice.first.bookstore.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

@Component
public class Token {
    private final String SECRET= "secretkey";
    public String createToken(String email){
        return JWT.create()
                .withClaim("email",email)
                .sign(Algorithm.HMAC256(SECRET));
    }

    public String decodeToken(String token){
        String id = "";
        if(token!=null){
            id = JWT.require(Algorithm.HMAC256(SECRET))
                    .build().verify(token)
                    .getClaim("email").asString();
            return id;
        }
        return null;
    }
}
