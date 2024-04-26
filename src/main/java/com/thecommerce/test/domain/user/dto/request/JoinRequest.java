package com.thecommerce.test.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원가입을 위한 요청 객체")
public class JoinRequest {
    @NotBlank(message = "회원의 로그인 ID를 입력해주세요.")
    @Size(max = 50, message = "로그인 ID는 최대 50이어야 합니다.")
    @Schema(description = "로그인 ID를 입력해주세요." , example = "thecommerce")
    private String loginId;

    @NotBlank(message = "회원의 비밀번호를 입력해 주세요.")
    @Size(min = 8, max = 16, message = "비밀번호는 8글자 이상 16글자 이하여야 합니다.")
    @Schema(description = "비밀번호를 입력해주세요." , example = "thecommerce123!")
    private String password;

    @NotBlank(message = "회원 닉네임을 입력해 주세요.")
    @Size(max = 50, message = "닉네임은 크기는 최대 50이어야 합니다.")
    @Schema(description = "닉네임을 입력해주세요." , example = "더커머스")
    private String nickname;

    @NotBlank(message = "회원 이름을 입력해 주세요.")
    @Size(max = 50, message = "이름은 크기는 최대 10이어야 합니다.")
    @Schema(description = "이름을 입력해주세요." , example = "더커머스")
    private String name;

    @NotBlank(message = "전화번호를 입력해 주세요.")
    @Size(max = 50, message = "전화번호의 크기는 최대 15이어야 합니다.")
    @Schema(description = "전화번호를 입력해주세요." , example = "010-xxxx-xxxx")
    private String phoneNumber;

    @NotBlank(message = "회원의 이메일을 입력해주세요.")
    @Email(message = "이메일 형식에 맞춰 입력해주세요.")
    @Size(max = 100, message = "이메일은 크기는 최대 100이어야 합니다.")
    @Schema(description = "이메일을 입력해주세요." , example = "thecommerce@gmail.com")
    private String email;
}
