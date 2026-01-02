package org.chandlercasey.quickscribe;

import org.chandlercasey.quickscribe.entities.User;
import org.chandlercasey.quickscribe.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class QuickScribeApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickScribeApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedDatabase(UserRepository userRepository) {
        return (args) -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String hashedPassword = encoder.encode("Pass");
            userRepository.save(new User("chandlercasey13@gmail.com", hashedPassword, "Chandler" ));
        };
    }
}
