package org.chandlercasey.quickscribe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/auth/login")
    public String Login(){
        return "Login";
    }

    @GetMapping("/auth/signup")
    public String SignUp(){
        return "Signup";
    }

}
