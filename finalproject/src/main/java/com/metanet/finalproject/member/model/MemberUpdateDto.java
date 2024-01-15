package com.metanet.finalproject.member.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberUpdateDto {

    private String memberEmail;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private String memberPhoneNumber;

    @NotBlank(message = "이름을 입력해주세요.")
    private String memberName;
}
