package com.francislainy.duchinesebe.service.impl;

import com.francislainy.duchinesebe.model.User;
import com.francislainy.duchinesebe.repository.UserRepository;
import com.francislainy.duchinesebe.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user.toEntity()).toModel();
    }
}
