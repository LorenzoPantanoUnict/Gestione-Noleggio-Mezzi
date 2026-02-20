package com.noleggiomezzi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        // Questa singola riga avvia l'Application Context di Spring e il server Tomcat integrato!
        SpringApplication.run(App.class, args);
        System.out.println("✅ Sistema di Noleggio avviato con Spring Boot! Server in ascolto su http://localhost:8080");
    }
}