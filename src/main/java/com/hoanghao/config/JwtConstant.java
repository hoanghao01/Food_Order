package com.hoanghao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

public class JwtConstant {
    public static final String SECRET_KEY="giaiphongmiennamthongnhatdatnuoc3004";
    public static final String JWT_HEADER="Authorization";

//    public static final long EXPIRATION_TIME=864000000; //10 days




    /*
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.jwtHeader}")
    private String jwtHeader;

    @Value("${jwt.expirationTime}")
    private long expirationTime;

    public String getSecretKey() {
        return secretKey;
    }

    public String getJwtHeader() {
        return jwtHeader;
    }

    public long getExpirationTime() {
        return expirationTime;
    }
    */

}
