package com.metanet.finalproject.member.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class MemberPasswordDto {
    private String memberEmail;
    @NotBlank(message = "원래 비밀번호를 입력하세요.")
    private String memberPassword;
    @NotBlank(message = "새로운 비밀번호를 입력하세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String newPassword;
}
