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

//hmm
@RequestMapping(path = "")
@Controller
public class LoginController {

    private UserService userService;
    private RoleService roleService;

    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public boolean isSignedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    //Index page shows login in or register
    @GetMapping(path = "/")
    public String index() {
        return "User/index";
    }

    @GetMapping(path = "/sessionTimeout")
    public String sessionTimeout() {
        return "User/sessionTimeout";
    }


    //Page users see when they login
    @GetMapping(path = "/home/{user_id}")
    public String homeOrg(Model model, @PathVariable int user_id) {
        model.addAttribute("user_id", user_id);
        return "User/home";
    }


    //Checks if user is in current session
    @GetMapping(path = "/login")
    public String isUserConnected(HttpSession session, Model model) {
        //Organization object from session
        User user = (User) session.getAttribute("user");

        //If not connected redirect to login page. if connected continue to home page
        if (user == null) {
            model.addAttribute("user", new User());
            return "User/login";
        } else {
            return "redirect:/home/" + user.getUser_id();
        }
    }

    //Sign in with user
    @PostMapping(path = "/login")
    public String login(HttpSession session, @ModelAttribute("user") User user, Model model) {
        try {
            User userLogin = userService.login(user.getUsername(), user.getPassword());
            if (userLogin != null) {
                session.setAttribute("user", userLogin);
                session.setMaxInactiveInterval(1500);

                return "redirect:/home/" + userLogin.getUser_id();
            } else {
                model.addAttribute("loginFailed", true);
                return "User/login";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //Sign up page
    @GetMapping(path = "/register")
    public String showSignUp(Model model) {
        List<Role> roles = roleService.getAllRoles();
        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "User/register";
    }

    //Sign up
    @PostMapping(path = "/register")
    public String register(@ModelAttribute("organization") User user, @RequestParam("role") int role_id, Model model) {
        boolean isUsernameTaken = userService.isUsernameTaken(user.getUsername());

        if (isUsernameTaken) {
            model.addAttribute("usernameTaken", true);
            return "User/register";
        }

        user.setRole_id(role_id);
        userService.register(user);
        return "redirect:/register";
    }


    //Sign out
    @GetMapping(path = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/register";
    }


    //Edit user page
    @GetMapping(path = "/editUser/{user_id}")
    public String showEditUser(@PathVariable("user_id") int user_id, Model model, HttpSession session) {

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
    public String editUser(@PathVariable("user_id") int user_id, @RequestParam("username") String user_name, String password, HttpSession session) {
        if (isSignedIn(session)) {
            User user = userService.getUserFromId(user_id);
            user.setUsername(user_name);
            user.setPassword(password);
            userService.editUser(user, user_id);
            return "redirect:/home/" + user_id;
        }
        return "redirect:/sessionTimeout";
    }
}
