package com.example.projectforge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

   @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(csrf -> csrf.disable())
            .authorizeRequests((requests) -> requests
                    .requestMatchers("/login", "/register", "/addProject", "/createProject").permitAll()
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



    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }


    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (role.equals(authority.getAuthority())) {
                    return true;
                }
            }
        }
        return false;
    }
}



// (note)  / route instead of /homepage, it could be due to the default behavior of Spring Security.
// If you don't specify a default success URL,
// Spring Security will redirect you to the root URL (/) after login.
    /*
    .formLogin((formLogin) -> formLogin
    .loginPage("/login")
    .defaultSuccessURL("/homepage", true)
    .permitAll()
)

     */

