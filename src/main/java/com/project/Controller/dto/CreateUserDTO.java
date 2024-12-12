package com.project.Controller.dto;

import com.project.models.RoleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {


    private String email;
    private String username;
    private String password;
    private Set<String> roles;
}
