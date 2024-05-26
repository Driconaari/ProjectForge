package com.example.projectforge.controller;

import com.example.ProjectForge.controller.RoleController;
import com.example.ProjectForge.model.Role;
import com.example.ProjectForge.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// test for getting all roles from role controller and role service with mockito and mockmvc for testing the controller and service
@WebMvcTest(RoleController.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;
    @Test
    public void getAllRolesTest() throws Exception {
        Role role1 = new Role(1, "Admin");
        role1.setId(1);
        role1.setName("Admin");

        Role role2 = new Role(1, "Admin");
        role2.setId(2);
        role2.setName("User");

        List<Role> allRoles = Arrays.asList(role1, role2);

        when(roleService.getAllRoles()).thenReturn(allRoles);

        mockMvc.perform(get("/roles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(role1.getId()))
                .andExpect(jsonPath("$[0].name").value(role1.getName()))
                .andExpect(jsonPath("$[1].id").value(role2.getId()))
                .andExpect(jsonPath("$[1].name").value(role2.getName()));
    }
}