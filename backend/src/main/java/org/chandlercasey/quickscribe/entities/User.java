package org.chandlercasey.quickscribe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private Instant updatedAt;
    private Instant createdAt;

    @PreUpdate
    void onUpdate() {
        this.updatedAt = Instant.now();
    }

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
        this.createdAt = Instant.now();

    }

    protected User() {}

    public User(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;
    }





}
