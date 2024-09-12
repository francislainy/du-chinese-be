package com.francislainy.duchinesebe.repository;

import com.francislainy.duchinesebe.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
