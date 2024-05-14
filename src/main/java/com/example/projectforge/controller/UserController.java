package com.example.projectforge.controller;

import com.example.projectforge.dto.UserRegistrationDTO;
import com.example.projectforge.model.User;
import com.example.projectforge.service.CustomUserDetailsService;
import com.example.projectforge.service.UserService;
import com.example.projectforge.userRepository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    private final UserRepository userRepository;

    private final UserService userService;

    public UserController(UserRepository userRepository, CustomUserDetailsService customUserDetailsService, UserService userService) {
        this.userRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
    }

    @GetMapping("/index/{user_id}")
    public String indexOrg(Model model, @PathVariable int user_id) {
        model.addAttribute("user_id", user_id);
        return "/index";
    }

    @GetMapping("/login")
    public String isUserConnected(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            model.addAttribute("user", new User());
            return "User/login";
        } else {
            return String.format("redirect:/index/%d", user.getUser_id());
        }
    }


@PostMapping("/login")
public String login(HttpSession session, @ModelAttribute("user") User user, Model model) {
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    if (passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute("user", userDetails);

        // Retrieve the username from the UserDetails object
        String username = userDetails.getUsername();

        // Use the username to fetch the User object from your UserRepository
        User userFromDb = userRepository.findByUsername(username);

        // Store the user ID in the session
        session.setAttribute("user_id", userFromDb.getUser_id());

        return "redirect:/index/" + userFromDb.getUser_id();
    } else {
        model.addAttribute("error", "true");
        return "User/login";
    }
}


    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "User/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserRegistrationDTO registrationDto) {
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(registrationDto.getPassword()));
        newUser.setEmail(registrationDto.getEmail());
        newUser.setRoles(registrationDto.getRoles());
        userRepository.register(newUser);
        return "redirect:/login";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        session.invalidate();
        return "redirect:/login";
    }


    //Edit user page
    @GetMapping(path = "/editUser/{user_id}")
    public String showEditUser(@PathVariable("user_id") long user_id, Model model, HttpSession session) {

        if (isSignedIn(session)) {
            model.addAttribute("user_id", user_id);
            User user = userService.getUserFromId(user_id);
            model.addAttribute("user", user);
            return "User/editUser";
        }
        return "redirect:/sessionTimeout";
    }

    //Edit user
    @PostMapping(path = "/editUser/{user_id}")
    public String editUser(@PathVariable("user_id") long user_id, @RequestParam("username") String user_name, String password, HttpSession session) {
        if (isSignedIn(session)) {
            User user = userService.getUserFromId(user_id);
            user.setUsername(user_name);
            user.setPassword(password);
            userService.editUser(user, user_id);
            return "redirect:/index/" + user_id;
        }
        return "redirect:/sessionTimeout";
    }

    public boolean isSignedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }
}

