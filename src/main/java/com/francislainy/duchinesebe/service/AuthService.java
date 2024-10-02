package com.francislainy.duchinesebe.service;

import com.francislainy.duchinesebe.model.User;

public interface AuthService {

    User registerUser(User user);
    User loginUser(User user);
    void logoutUser();
}
