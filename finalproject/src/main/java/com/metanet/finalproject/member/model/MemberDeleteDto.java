package com.metanet.finalproject.member.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberDeleteDto {
    private String memberEmail;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String memberPassword;
}
