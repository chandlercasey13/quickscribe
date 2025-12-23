package org.chandlercasey.quickscribe;

import org.chandlercasey.quickscribe.entities.User;
import org.chandlercasey.quickscribe.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuickScribeApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickScribeApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedDatabase(UserRepository userRepository) {
        return (args) -> {
            userRepository.save(new User("chandlercasey13@gmail.com","StrongPass", "Chandler" ));
        };
    }
}
