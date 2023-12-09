package com.CyHawkClash.Backend.Config;

import com.CyHawkClash.Backend.Models.User.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class BackendInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getServletPath().equals("/users") && request.getMethod().equals("POST")){
            return true;
        } else if(request.getServletPath().equals("/login")) {
            return true;

        }else if(request.getServletPath().startsWith("/swagger-ui") || request.getServletPath().startsWith("/v3/")) {
            return true;
        }
        String token = request.getHeader("Authorization");
        try {
            int id = Integer.parseInt(com.CyHawkClash.Backend.Views.response.decode_jwt(token));
            if(userRepository.findById(id) == null || userRepository.findById(id).isDeleted()){
                response.sendError(403, "User not Registered");
                return false;
            }
        } catch(Exception e) {
            response.sendError(403, "User is logged out or not Registered");
            return false;
        }

        return true;
    }
}
