package com.example.userManagement.controller;

import com.example.userManagement.model.User;
import com.example.userManagement.model.Role;
import com.example.userManagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    // creating user
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // only admin can get all users
    @GetMapping("/get")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // block/unblock user - only admin
    @PutMapping("/users/{id}/block")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> blockUser(@PathVariable String id, @RequestParam boolean blocked) throws Exception {
        return ResponseEntity.ok(userService.blockUser(id, blocked));
    }

    // assign roles - only admin
    @PutMapping("/users/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> assignRoles(@PathVariable String id, @RequestBody Set<Role> roles) throws Exception {
        return ResponseEntity.ok(userService.assignRoles(id, roles));
    }

}
