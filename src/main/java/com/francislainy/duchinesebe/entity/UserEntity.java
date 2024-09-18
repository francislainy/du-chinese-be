package com.francislainy.duchinesebe.entity;

import com.francislainy.duchinesebe.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String username;
    private String password;

    private String firstName;
    private String lastName;
    private String role;

    @ManyToMany
    @JoinTable(
            name = "user_favourites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<LessonEntity> favouritedLessons = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_reads",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id")
    )
    private Set<LessonEntity> readLessons = new HashSet<>();

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
