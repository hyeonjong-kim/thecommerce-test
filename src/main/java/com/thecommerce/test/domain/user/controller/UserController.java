package com.thecommerce.test.domain.user.controller;

import com.thecommerce.test.domain.user.dto.request.JoinRequest;
import com.thecommerce.test.domain.user.dto.request.ModifyUserRequest;
import com.thecommerce.test.domain.user.dto.response.ModifyUserResponse;
import com.thecommerce.test.domain.user.service.UserService;
import com.thecommerce.test.global.common.code.SuccessCode;
import com.thecommerce.test.global.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Tag(name = "회원 API", description = "회원 관리 용 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "사용자는 회원 정보를 입력하여 회원가입을 합니다.")
    public ResponseEntity<SuccessResponse<?>> join(@Valid @RequestBody JoinRequest request) {
        userService.addUser(request);
        SuccessResponse<?> response = SuccessResponse.builder()
                .data(true)
                .status(SuccessCode.INSERT_SUCCESS.getStatus())
                .message(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{loginId}")
    @Operation(summary = "회원 정보 수정", description = "사용자는 회원 정보를 수정합니다.")
    public ResponseEntity<SuccessResponse<?>> modifyUser(@PathVariable String loginId, @Valid @RequestBody ModifyUserRequest request) {
        ModifyUserResponse modifyUserResponse = userService.updateUser(loginId, request);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.UPDATE_SUCCESS.getStatus())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .data(modifyUserResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
