package com.noleggiomezzi.utility;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**") // Proteggi tutto di default
                .excludePathPatterns(
                    "/login",           // Login Cassiere
                    "/login-cliente",   // Login Cliente
                    "/registra-cliente",// Registrazione (deve essere libera!)
                    "/prenota/ricerca", // Ricerca mezzi (pubblica)
                    "/prenota/risultati", // Risultati ricerca (pubblica)
                    "/css/**",          // Risorse statiche
                    "/js/**", 
                    "/images/**",
                    "/error"
                ); 
    }
}