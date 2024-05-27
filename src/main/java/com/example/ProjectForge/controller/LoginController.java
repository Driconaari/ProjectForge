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


//Login controller with login, register, logout, edit user, home and session timeout pages

//Path for login controller is /login and all pages are under /login path with different paths for each page in controller class
// below with @RequestMapping annotation for each page path in controller class and @GetMapping and @PostMapping annotations for each
@RequestMapping(path = "")
@Controller
public class LoginController {

    private UserService userService;
    private RoleService roleService;

    public LoginController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

//Check if user is signed in with session and return true or false if user is signed in or not,
// signed in with session attribute user from session object
    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    //Index page shows login in or register page for user
    @GetMapping(path = "/")
    public String index() {
        return "User/index";
    }

    //Page users see when session times out and they need to login again
    @GetMapping(path = "/sessionTimeout")
    public String sessionTimeout() {
        return "User/sessionTimeout";
    }


    //Page users see when they login with user_id
    @GetMapping(path = "/home/{user_id}")
    public String homeOrg(Model model, @PathVariable int user_id) {
        model.addAttribute("user_id", user_id);
        return "User/home";
    }


    //Checks if user is in current session and redirects to login page if not connected
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


    //Sign in with user and redirect to home page with user_id
   @PostMapping(path = "/login")
public String login(HttpSession session, @ModelAttribute("user") User user, Model model) {
    try {
        User userLogin = userService.login(user.getUsername(), user.getPassword());
        if (userLogin != null) {
            session.setAttribute("user", userLogin);
            session.setMaxInactiveInterval(1500);

            // If the user has a role, set it in the session
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


    //Sign up page with user object and role_id from database to sign up user
    @GetMapping(path = "/register")
    public String showSignUp(Model model) {
        List<Role> roles = roleService.getAllRoles();
        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("defualtRole", 1);//Default role is 1 for user role in database table role (1 = user, 2 = admin)
        return "User/register";
    }

    //Sign up with user and redirect to login page
   @PostMapping(path = "/register")
public String register(@ModelAttribute("user") User user, @RequestParam("role_id") int role_id, @RequestParam("email") String email, Model model) {
    boolean isUsernameTaken = userService.isUsernameTaken(user.getUsername());

    user.setRole_id(role_id);

    if (isUsernameTaken) {
        model.addAttribute("usernameTaken", true);
        return "User/register";
    }

    user.setRole_id(role_id);
    user.setEmail(email); // Set the email field on the user
    userService.register(user);
    return "redirect:/login";
}


    //Sign out and redirect to login page
    @GetMapping(path = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/register";
    }


    //Edit user page with user_id and user object from database to edit user information in database
    @GetMapping(path = "/editUser/{user_id}")
    public String showEditUser(@PathVariable("user_id") int user_id, Model model, HttpSession session) {

        if (isLoggedIn(session)) {
            model.addAttribute("user_id", user_id);
            User user = userService.getUserFromId(user_id);
            model.addAttribute("user", user);
            return "User/editUser";
        }
        return "redirect:/sessionTimeout";
    }

    //Edit user information in database and redirect to home page with user_id
    @PostMapping(path = "/editUser/{user_id}")
    public String editUser(@PathVariable("user_id") int user_id, @RequestParam("username") String user_name, String password, HttpSession session) {
        if (isLoggedIn(session)) {
            User user = userService.getUserFromId(user_id);
            user.setUsername(user_name);
            user.setPassword(password);
            userService.editUser(user, user_id);
            return "redirect:/home/" + user_id;
        }
        return "redirect:/sessionTimeout";
    }
}
