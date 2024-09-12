package com.francislainy.duchinesebe.entity;

import com.francislainy.duchinesebe.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;
    private String password;

//    todo: add default roles (USER) and way to make user an admin (adding more roles to the list) - 2024-09-12

    // map to model
    public User toModel() {
        return User.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .build();
    }
}
