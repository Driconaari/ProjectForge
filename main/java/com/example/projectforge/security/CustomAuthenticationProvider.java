package com.example.projectforge.security;

import com.example.projectforge.model.User;
import com.example.projectforge.service.CustomUserDetailsService;
import com.example.projectforge.userRepository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


@Autowired
private UserRepository userRepository;

@Override
public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    log.debug("Authenticating user");
    if (authentication instanceof UsernamePasswordAuthenticationToken) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("Password does not match for user: " + username);
            throw new BadCredentialsException("Invalid password");
        }
        System.out.println("Password matches for user: " + username);

        // Fetch the custom User object from the database
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in custom repository");
        }
        long userId = user.getUser_id();
        System.out.println("User ID: " + userId);

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    } else {
        throw new ClassCastException("Authentication object is not of type UsernamePasswordAuthenticationToken");
    }
}

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}