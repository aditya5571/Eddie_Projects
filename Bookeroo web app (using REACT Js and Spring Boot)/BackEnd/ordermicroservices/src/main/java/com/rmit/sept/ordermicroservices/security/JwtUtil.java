package com.rmit.sept.ordermicroservices.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtil {
    static public Long getUserIdFromJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(SecurityConstant.SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");

        return Long.parseLong(id);
    }
}
