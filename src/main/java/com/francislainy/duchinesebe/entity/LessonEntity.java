package com.francislainy.duchinesebe.entity;

import com.francislainy.duchinesebe.model.Lesson;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    // map to model
    public Lesson toModel() {
        return Lesson.builder()
                .id(this.id)
                .date(this.date)
                .type(this.type)
                .imageUrl(this.imageUrl)
                .title(this.title)
//                .content(this.content)
                .level(this.level)
                .build();
    }
}
