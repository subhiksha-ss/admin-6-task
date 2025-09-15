package com.example.userManagement.service;
import com.example.userManagement.model.User;
import com.example.userManagement.model.Role;
import com.example.userManagement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//import java.util.Optional;
import java.util.Set;


@ Service

public class UserService {

	@Autowired
	private UserRepository userRepository;


	// create user
	public User createUser(User user){

		return userRepository.save(user);
	}


    // get all users 
	public List<User> getAllUsers(){

		return userRepository.findAll();
	}


	//role setup

	public User assignRoles(String userId, Set<Role> roles) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        user.setRoles(roles);

        return userRepository.save(user);
    }

	// block and unblock user 

	public User blockUser(String userId , boolean blocked)throws Exception{

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new Exception("User not found"));

		user.setBlocked(blocked);

		return userRepository.save(user);
	}

}

