package com.project.Controller;

import com.project.Controller.dto.CreateUserDTO;
import com.project.models.ERole;
import com.project.models.RoleEntity;
import com.project.models.UserEntity;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PrincipalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createUser")
    public ResponseEntity<?>createUser(@RequestBody CreateUserDTO userDTO){
        Set<RoleEntity> roles = userDTO.getRoles().stream().map( role -> RoleEntity.builder().name(ERole.valueOf(role))
                .build()).collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam String id){
        userRepository.deleteById(Long.valueOf(id));
        return "Se ha borrado el usuario con id".concat(id);
    }
}