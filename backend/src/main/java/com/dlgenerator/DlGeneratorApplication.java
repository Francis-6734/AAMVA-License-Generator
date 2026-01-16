package com.dlgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DlGeneratorApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DlGeneratorApplication.class, args);
        printBanner();
    }
    
    private static void printBanner() {
        System.out.println("\n" +
            "╔══════════════════════════════════════════════════════╗\n" +
            "║   DRIVER'S LICENSE GENERATOR - PROFESSIONAL EDITION ║\n" +
            "║                                                      ║\n" +
            "║   API Running: http://localhost:8080                 ║\n" +
            "║   Health Check: http://localhost:8080/api/license/health ║\n" +
            "║   Swagger UI: http://localhost:8080/swagger-ui.html ║\n" +
            "║                                                      ║\n" +
            "║   Status: ✓ READY                                   ║\n" +
            "╚══════════════════════════════════════════════════════╝\n"
        );
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:3000", "http://localhost:3001")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
            }
        };
    }
}