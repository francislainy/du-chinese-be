package com.francislainy.duchinesebe.model;

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
//    private String content;
    private String level;
}
