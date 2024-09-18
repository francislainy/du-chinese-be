package com.francislainy.duchinesebe.entity;

import com.francislainy.duchinesebe.model.Lesson;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "lessons")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonEntity {

    @Id
    @GeneratedValue
    private UUID id;
    private LocalDate date;
    private String type;
    private String imageUrl;
    private String title;
    private String content;
    private String level;

    @Transient
    private boolean favouritedByCurrentUser;

    @Transient
    private boolean readByCurrentUser;

    @ManyToMany(mappedBy = "favouritedLessons")
    private Set<UserEntity> favouritedByUsers = new HashSet<>();

    // map to model
    public Lesson toModel() {
        return Lesson.builder()
                .id(this.id)
                .date(this.date)
                .type(this.type)
                .imageUrl(this.imageUrl)
                .title(this.title)
                .content(this.content)
                .level(this.level)
                .favouritedByCurrentUser(this.favouritedByCurrentUser)
                .readByCurrentUser(this.readByCurrentUser)
                .build();
    }
}