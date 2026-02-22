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

        boolean isClienteLoggato = (session != null && session.getAttribute("clienteLoggato") != null);
        boolean isCassiereLoggato = (session != null && session.getAttribute("utenteLoggato") != null);

        if (path.startsWith("/prenota")) {
            if (isClienteLoggato) {
                return true; 
            } else {
                response.sendRedirect("/login-cliente");
                return false;
            }
        }
        
        if (isCassiereLoggato) {
            return true; 
        } else {
            response.sendRedirect("/login");
            return false;
        }
    }
}