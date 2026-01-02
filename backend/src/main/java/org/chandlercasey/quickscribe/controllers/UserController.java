package org.chandlercasey.quickscribe.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chandlercasey.quickscribe.controllers.dto.LoginRequest;
import org.chandlercasey.quickscribe.entities.User;
import org.chandlercasey.quickscribe.repositories.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @GetMapping("/auth/login")
    public ResponseEntity<String> Login(){

        return ResponseEntity.ok().body("String");
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<String> SignUp(@RequestBody User user){
        try {
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashedPassword);
            userRepository.save(user);
            Long Id = user.getId();
            return ResponseEntity.status(HttpStatus.CREATED).body("User successfully created");

        } catch (DataIntegrityViolationException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("An error occured" + exception.getMessage());
        }

    }
    @GetMapping("/auth/user")
    public ResponseEntity<String> User(Authentication authenticate){

       return ResponseEntity.ok().body("User details");

    }

}
