package org.chandlercasey.quickscribe.controllers;

import jakarta.validation.Valid;
import org.chandlercasey.quickscribe.controllers.dto.LoginRequest;
import org.chandlercasey.quickscribe.entities.User;
import org.chandlercasey.quickscribe.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @PostMapping("/auth/login")
    public ResponseEntity Login(@Valid @RequestBody LoginRequest loginRequest){
        // currently this checks if user is there, and will return unauthorized if there is no user or the wrong password was used
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            Optional<User> userOpt = userRepository.findByEmail(loginRequest.email());

            if (userOpt.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Email does not Exist"));
            }
            User user = userOpt.get();

            if (!encoder.matches(loginRequest.password(), user.getPassword())) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Wrong Password"));
            }


        return ResponseEntity.ok().body(Map.of("Message","Success"));




    }

    @PostMapping("/auth/signup")
    public String SignUp(){
        if (userRepository.existsByEmail("chandlercasey13@gmail.com")){
            return "Successful Query";
        }

        return "Signup";
    }

}
