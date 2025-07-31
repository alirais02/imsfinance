package com.ims.imsfinance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .anyRequest().permitAll() // Mengizinkan semua request tanpa otorisasi
            .and()
            .csrf().disable(); // Menonaktifkan CSRF untuk kemudahan (jangan lakukan ini di produksi)

        return http.build();
    }
}
