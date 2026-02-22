package com.noleggiomezzi.utility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        HttpSession session = request.getSession(false); 
        String path = request.getRequestURI();

        if (session != null && (session.getAttribute("utenteLoggato") != null || 
                                session.getAttribute("clienteLoggato") != null)) {
            return true;
        }

        if (path.startsWith("/prenota/conferma")) {
            response.sendRedirect("/login-cliente");
        } else {
            response.sendRedirect("/login");
        }

        return false; 
    }
}