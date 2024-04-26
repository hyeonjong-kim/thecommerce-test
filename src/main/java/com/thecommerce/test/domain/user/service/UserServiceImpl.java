package com.thecommerce.test.domain.user.service;

import com.thecommerce.test.domain.user.dto.common.UserDto;
import com.thecommerce.test.domain.user.dto.request.JoinRequest;
import com.thecommerce.test.domain.user.dto.request.ModifyUserRequest;
import com.thecommerce.test.domain.user.dto.response.GetUserListResponse;
import com.thecommerce.test.domain.user.dto.response.ModifyUserResponse;
import com.thecommerce.test.domain.user.entity.User;
import com.thecommerce.test.domain.user.repository.UserRepository;
import com.thecommerce.test.global.common.code.ErrorCode;
import com.thecommerce.test.global.exception.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void addUser(JoinRequest addUserRequest) {
        if(userRepository.existsByEmail(addUserRequest.getEmail()))
            throw new BusinessExceptionHandler(ErrorCode.ALREADY_REGISTERED_EMAIL);

        User user = User.builder()
                .loginId(addUserRequest.getLoginId())
                .password(passwordEncoder.encode(addUserRequest.getPassword()))
                .nickname(addUserRequest.getNickname())
                .name(addUserRequest.getName())
                .phoneNumber(addUserRequest.getPhoneNumber())
                .email(addUserRequest.getEmail())
                .build();

        userRepository.save(user);
    }

    @Override
    public ModifyUserResponse updateUser(String loginId, ModifyUserRequest request) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new BusinessExceptionHandler("해당 회원이 존재하지 않습니다.", ErrorCode.NOT_FOUND_ERROR)
        );

        if(!passwordEncoder.matches(request.getBeforePassword(), user.getPassword()))
            throw new BusinessExceptionHandler("비밀번호가 일치하지 않습니다.", ErrorCode.NOT_MATCH_PASSWORD);

        user.setPassword(passwordEncoder.encode(request.getAfterPassword()));
        user.setNickname(request.getNickname());
        user.setName(request.getName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());

        userRepository.save(user);

        return ModifyUserResponse.builder()
                .loginId(user.getLoginId())
                .password(request.getAfterPassword())
                .nickname(request.getNickname())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .build();
    }

    @Override
    public GetUserListResponse selectUsers(Integer page, Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by("joinAt").ascending()
                .and(Sort.by("name").ascending()));

        Page<User> users = userRepository.findAll(pageable);
        if(users == null)throw new BusinessExceptionHandler("유저가 존재하지 않습니다.", ErrorCode.NOT_FOUND_ERROR);

        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(UserDto.toDto(user));
        }

        return GetUserListResponse.builder()
                .page(page)
                .pageSize(pageSize)
                .totalPage(users.getTotalPages())
                .totalSize(users.getTotalElements())
                .userList(userDtos)
                .build();
    }
}
