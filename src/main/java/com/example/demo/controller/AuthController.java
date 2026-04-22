package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.config.JwtUtil;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;
    
    @GetMapping("/")
    public String root() {
    	return "Api Running";
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        repo.save(user);
        return "Registered Successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User existingUser = repo.findByUsername(user.getUsername());

        if(existingUser != null &&
           existingUser.getPassword().equals(user.getPassword())) {

            String token = jwtUtil.generateToken(user.getUsername());
            return token;  
        }

        return "Invalid Credentials";
    }
}