package com.example.ProjectForge.repository;

/*
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import com.example.ProjectForge.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleRepositoryTest {

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void testGetAllRoles() {
        //Test data
        Role role1 = new Role(1, "User");
        Role role2 = new Role(2, "Admin");
        List<Role> expectedRoles = Arrays.asList(role1, role2);

        //Get all roles method
        when(roleRepository.getAllRoles()).thenReturn(expectedRoles);

        //Verify the roles are retrieved
        List<Role> actualRoles = roleRepository.getAllRoles();
        assertEquals(expectedRoles, actualRoles);
    }

    @Test
    public void testDoesRoleIdExist() {
        //Test data
        int roleId = 1;

        //Does role id exist method
        when(roleRepository.doesRoleIdExist(roleId)).thenReturn(true);

        //Verify the role id exists
        boolean doesExist = roleRepository.doesRoleIdExist(roleId);
        assertTrue(doesExist);
    }
}

 */