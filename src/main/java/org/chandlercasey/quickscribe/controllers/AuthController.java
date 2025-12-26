package org.chandlercasey.quickscribe.controllers;

import org.chandlercasey.quickscribe.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @GetMapping("/auth/login")
    public String Login(){
        return "Login";
    }

    @GetMapping("/auth/signup")
    public String SignUp(){
        if (userRepository.existsByEmail("chandlercasey13@gmail.com")){
            return "Successful Query";
        }

        return "Signup";
    }

}
