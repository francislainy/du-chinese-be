package com.francislainy.duchinesebe.service.impl;

import com.francislainy.duchinesebe.entity.UserEntity;
import com.francislainy.duchinesebe.enums.UserType;
import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.repository.UserRepository;
import com.francislainy.duchinesebe.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        UserEntity userEntity = user.toEntity();
        userEntity.setRole(UserType.USER.toString());
        return userRepository.save(userEntity).toModel();
    }

    @Override
    public User loginUser(User user) {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!userEntity.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return userEntity.toModel();
    }

    @Override
    public void logoutUser() {

    }
}
