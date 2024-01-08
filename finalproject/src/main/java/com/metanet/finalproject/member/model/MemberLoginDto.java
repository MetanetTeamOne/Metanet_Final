package com.metanet.finalproject.member.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberLoginDto {
    @NotBlank(message = "이메일을 입력하세요.")
    private String memberEmail;

    @NotBlank(message = "비밀번호를 입력하세요")
    private String memberPassword;
}
