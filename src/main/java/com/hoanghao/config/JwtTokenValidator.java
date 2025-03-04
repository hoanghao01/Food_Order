package com.hoanghao.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

public class JwtTokenValidator extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        // Bearer token
        if(jwt != null){
            //validate token
            //if token is valid, set the authentication in the context
            //if token is invalid, set the response status to unauthorized
            jwt = jwt.substring(7);

            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                // Get email and authorities from token
                String email = String.valueOf(claims.get("email")); //get email from token and set it in the context
                String authorities = String.valueOf(claims.get("authorities")); //get authorities from token and set it in the context

                // convert authorities to list of GrantedAuthority
                // ROLE_CUSTOMER, ROLE_ADMIN
                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);  //convert authorities to list of GrantedAuthority

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auth); //create authentication object
                SecurityContextHolder.getContext().setAuthentication(authentication); //set authentication in the context
            }
            catch (Exception e) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new BadCredentialsException("Invalid token...");
            }
        }

        filterChain.doFilter(request, response);

    }
}
