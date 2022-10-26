package com.microservices.authsvc.util;

import com.microservices.authsvc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String authorizationHeader = request.getHeader("Authorization");
       if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
           String extractedToken = authorizationHeader.substring(7);
           String userName=jwtTokenUtil.getUsernameFromToken(extractedToken);
           UserDetails user= userService.loadUserByUsername(userName);
            if(jwtTokenUtil.validateToken(extractedToken,user)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()
                );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
       }
        filterChain.doFilter(request,response);

    }
}
