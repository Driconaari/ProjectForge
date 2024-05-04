package com.example.projectforge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Autowired
    public  void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http
                 .authorizeRequests((requests) -> requests
                            .requestMatchers("/login","register").permitAll()
                         .requestMatchers("/homepage").hasRole("USER")
                         .requestMatchers("/admin/**").hasRole("ADMIN")
                         //ensure that all other requests are authenticated
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