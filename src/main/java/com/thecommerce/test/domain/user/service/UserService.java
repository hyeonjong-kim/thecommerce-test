package com.thecommerce.test.domain.user.service;

import com.thecommerce.test.domain.user.dto.request.JoinRequest;

public interface UserService {
    void addUser(JoinRequest addUserRequest);
}
