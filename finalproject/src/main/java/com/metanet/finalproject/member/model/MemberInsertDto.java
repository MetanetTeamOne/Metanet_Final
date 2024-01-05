package com.metanet.finalproject.member.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberInsertDto {
    @NotBlank(message = "이름은 필수 입력값 입니다.")
    private String memberName;

    @NotBlank(message = "이메일은 필수 입력값 입니다.")
    private String memberEmail;
    private String memberPassword;
    private String memberPhoneNumber;
    private String addressZipcode;
    private String addressRoad;
    private String addressContent;
}
