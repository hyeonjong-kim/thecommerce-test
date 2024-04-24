package com.thecommerce.test.domain.user.service;

import com.thecommerce.test.domain.user.dto.request.JoinRequest;
import com.thecommerce.test.domain.user.entity.User;
import com.thecommerce.test.domain.user.repository.UserRepository;
import com.thecommerce.test.global.common.code.ErrorCode;
import com.thecommerce.test.global.exception.BusinessExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void addUser(JoinRequest addUserRequest) {
        // 이메일 중복인 경우 강제 에러 발생
        try {
            if(userRepository.existsByEmail(addUserRequest.getEmail()))
                throw new BusinessExceptionHandler(ErrorCode.ALREADY_REGISTERED_EMAIL);
        } catch (Exception e) {
            throw new BusinessExceptionHandler(ErrorCode.ALREADY_REGISTERED_EMAIL);
        }

        // User 엔티티 생성
        User user = User.builder()
                .loginId(addUserRequest.getLoginId())
                .password(passwordEncoder.encode(addUserRequest.getPassword()))
                .nickname(addUserRequest.getNickname())
                .name(addUserRequest.getName())
                .phoneNumber(addUserRequest.getPhoneNumber())
                .email(addUserRequest.getEmail())
                .build();

        // 회원 저장
        userRepository.save(user);
    }
}
