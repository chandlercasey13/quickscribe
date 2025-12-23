package org.chandlercasey.quickscribe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/auth/login")
    public String Login(){
        return "YOOOO";
    }

    @GetMapping("/auth/signup")
    public String SignUp(){
//        check DB for username
//        if user exists: return "Username already taken"
//        else: createaccount
        return "SIGNUP";
    }

}
