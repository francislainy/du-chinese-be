package com.francislainy.duchinesebe.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.francislainy.duchinesebe.entity.LessonEntity;
import com.francislainy.duchinesebe.model.views.LessonViews;
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

    @JsonView(LessonViews.ListView.class)
    private UUID id;

    @JsonView(LessonViews.ListView.class)
    private LocalDate date;

    @JsonView(LessonViews.ListView.class)
    private String type;

    @JsonView(LessonViews.ListView.class)
    private String imageUrl;

    @JsonView(LessonViews.ListView.class)
    private String title;

    @JsonView(LessonViews.ListView.class)
    private String description;

    @JsonView(LessonViews.ListView.class)
    private String level;

    @JsonView(LessonViews.ListView.class)
    private boolean favouritedByCurrentUser;

    @JsonView(LessonViews.ListView.class)
    private boolean readByCurrentUser;

    @JsonView(LessonViews.DetailView.class)
    private String content;  // Include in detail view only

    // map to entity
    public LessonEntity toEntity() {
        return LessonEntity.builder()
                .id(this.id)
                .date(this.date)
                .type(this.type)
                .imageUrl(this.imageUrl)
                .title(this.title)
                .description(this.description)
                .level(this.level)
                .favouritedByCurrentUser(this.favouritedByCurrentUser)
                .readByCurrentUser(this.readByCurrentUser)
                .content(this.content)
                .build();
    }
}
