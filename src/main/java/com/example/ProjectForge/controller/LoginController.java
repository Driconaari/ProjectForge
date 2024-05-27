package com.example.ProjectForge.controller;

import com.example.ProjectForge.model.Role;
import com.example.ProjectForge.model.User;
import com.example.ProjectForge.service.RoleService;
import com.example.ProjectForge.service.UserService;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class LoginController {

    private final UserService userService;
    private final RoleService roleService;

    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    @GetMapping
    public String index() {
        return "User/index";
    }

    @GetMapping("/sessionTimeout")
    public String sessionTimeout() {
        return "User/sessionTimeout";
    }

    @GetMapping("/home/{user_id}")
    public String homeOrg(Model model, @PathVariable("user_id") int userId) {
        model.addAttribute("user_id", userId);
        return "User/home";
    }

    @GetMapping("/login")
    public String isUserConnected(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            model.addAttribute("user", new User());
            return "User/login";
        } else {
            return "redirect:/home/" + user.getUser_id();
        }
    }

    @PostMapping("/login")
    public String login(HttpSession session, @ModelAttribute("user") User user, Model model) {
        try {
            User userLogin = userService.login(user.getUsername(), user.getPassword());
            if (userLogin != null) {
                session.setAttribute("user", userLogin);
                session.setMaxInactiveInterval(1500);

                if (userLogin.getRole_id() != 0) {
                    session.setAttribute("role", userLogin.getRole_id());
                }

                return "redirect:/home/" + userLogin.getUser_id();
            } else {
                model.addAttribute("loginFailed", true);
                return "User/login";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/register")
    public String showSignUp(Model model) {
        List<Role> roles = roleService.getAllRoles();
        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("defaultRole", 1);
        return "User/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, @RequestParam("role_id") int roleId, @RequestParam("email") String email, Model model) {
        if (userService.isUsernameTaken(user.getUsername())) {
            model.addAttribute("usernameTaken", true);
            return "User/register";
        }

        user.setRole_id(roleId);
        user.setEmail(email);
        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/register";
    }

    @GetMapping("/editUser/{user_id}")
    public String showEditUser(@PathVariable("user_id") int userId, Model model, HttpSession session) {
        if (isLoggedIn(session)) {
            User user = userService.getUserFromId(userId);
            model.addAttribute("user", user);
            return "User/editUser";
        }
        return "redirect:/sessionTimeout";
    }

    @PostMapping("/editUser/{user_id}")
    public String editUser(@PathVariable("user_id") int userId, @RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        if (isLoggedIn(session)) {
            User user = userService.getUserFromId(userId);
            user.setUsername(username);
            user.setPassword(password);
            userService.editUser(user, userId);
            return "redirect:/home/" + userId;
        }
        return "redirect:/sessionTimeout";
    }
}
