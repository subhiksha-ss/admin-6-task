package com.example.userManagement.controller;
import com.example.userManagement.model.User;
import com.example.userManagement.model.Role;
import com.example.userManagement.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController

@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    // creating user
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // getting all user
    @GetMapping("/get")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    

    // blocking user
    @PutMapping("/users/{id}/block")
    public User blockUser(@PathVariable String id, @RequestParam boolean blocked) throws Exception {
        return userService.blockUser(id, blocked);
    }

    // Assign roles
    @PutMapping("/users/{id}/roles")
    public User assignRoles(@PathVariable String id, @RequestBody Set<Role> roles) throws Exception {
        return userService.assignRoles(id, roles);
    }
}

