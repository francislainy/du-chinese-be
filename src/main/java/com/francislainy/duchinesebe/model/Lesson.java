package com.francislainy.duchinesebe.model;

import com.francislainy.duchinesebe.entity.LessonEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Lesson {

    private UUID id;
    private LocalDate date;
    private String type;
    private String imageUrl;
    private String title;
    private String content;
    private String level;

    private boolean favouritedByCurrentUser;

    // map to entity
    public LessonEntity toEntity() {
        return LessonEntity.builder()
                .id(this.id)
                .date(this.date)
                .type(this.type)
                .imageUrl(this.imageUrl)
                .title(this.title)
                .content(this.content)
                .level(this.level)
                .favouritedByCurrentUser(this.favouritedByCurrentUser)
                .build();
    }
}
