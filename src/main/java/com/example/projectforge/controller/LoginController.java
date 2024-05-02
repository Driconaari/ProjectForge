package com.example.projectforge.controller;

import com.example.projectforge.dto.UserRegistrationDto;
import com.example.projectforge.model.User;
import com.example.projectforge.repository.UserRepo;
import com.example.projectforge.security.WebSecurityConfigurerAdapter;
import com.example.projectforge.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.projectforge.repository.UserRepo;

@Controller
public class LoginController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/";
        } else {
            model.addAttribute("error", "true");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(registrationDto.getPassword()));
        userRepo.register(newUser);
        return "redirect:/login";
    }
}