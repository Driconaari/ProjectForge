package com.example.projectforge.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http
                 .authorizeRequests((requests) -> requests
                            .requestMatchers("/login","register").permitAll()
                            .anyRequest().authenticated()
                 )
                 .formLogin((formLogin) -> formLogin
                            .loginPage("/login")
                            .permitAll()
                 )
                 .logout((logout) -> logout
                            .logoutUrl("/logout")
                            .permitAll()
                 );

        return http.build();
    }

}