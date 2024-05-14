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
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf ->csrf.disable())
               // .requiresChannel(channel -> channel.anyRequest().requiresSecure())// Enforces HTTPS //  ensures that the information exchanged between your website and its visitors is encrypted and won't be intercepted by third parties.
                .authorizeRequests((requests) -> requests
                        .requestMatchers("/login", "register", "/createProject").permitAll()
                        .requestMatchers("/homepage").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        //ensure that all other requests are authenticated
                        .anyRequest().authenticated()
                )
                //creates its own http request for login
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

}