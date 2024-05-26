package com.example.projectforge.controller;

import com.example.ProjectForge.controller.LoginController;
import com.example.ProjectForge.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.example.ProjectForge.service.RoleService;
import com.example.ProjectForge.service.UserService;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// Test class for LoginController with login, register, logout, edit user, home and session timeout pages
// Path for login controller is /login and all pages are under /login path with different paths for each page in controller class
// below with @RequestMapping annotation for each page path in controller class and @GetMapping and @PostMapping annotations for each
// Check if user is signed in with session and return true or false if user is signed in or not,
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void testLoginSuccess() throws Exception {
        User user = new User();
        user.setUser_id(1);
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(userService.login(anyString(), anyString())).thenReturn(user);

        mockMvc.perform(post("/login")
                .param("username", "testUser")
                .param("password", "testPassword"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home/" + user.getUser_id()));
    }

    @Test
    public void testLoginFailure() throws Exception {
        when(userService.login(anyString(), anyString())).thenReturn(null);

        mockMvc.perform(post("/login")
                .param("username", "testUser")
                .param("password", "wrongPassword"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("loginFailed", true))
                .andExpect(view().name("User/login"));
    }
}