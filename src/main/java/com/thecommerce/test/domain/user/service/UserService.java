package com.thecommerce.test.domain.user.service;

import com.thecommerce.test.domain.user.dto.request.JoinRequest;
import com.thecommerce.test.domain.user.dto.request.ModifyUserRequest;
import com.thecommerce.test.domain.user.dto.response.GetUserListResponse;
import com.thecommerce.test.domain.user.dto.response.ModifyUserResponse;

public interface UserService {
    void addUser(JoinRequest addUserRequest);
    ModifyUserResponse updateUser(String loginId, ModifyUserRequest request);
    GetUserListResponse selectUsers(Integer page, Integer pageSize);
}
