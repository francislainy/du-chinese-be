package com.francislainy.duchinesebe.model;

import com.francislainy.duchinesebe.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {

    private UUID id;
    private String username;
    private String password;
    private String role;

    // map to entity
    public UserEntity toEntity() {
        return UserEntity.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .role(this.role)
                .build();
    }
}
