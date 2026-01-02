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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;



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

}
