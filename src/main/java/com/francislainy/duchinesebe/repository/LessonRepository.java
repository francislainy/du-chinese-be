package com.francislainy.duchinesebe.repository;

import com.francislainy.duchinesebe.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {
}
