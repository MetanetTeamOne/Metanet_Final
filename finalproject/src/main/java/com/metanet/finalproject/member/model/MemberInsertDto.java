package com.metanet.finalproject.member.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberInsertDto {
    @NotBlank(message = "이름은 필수 입력값 입니다.")
    @Size(min = 2, max = 8, message = "이름은 2자에서 8자사이로 만들어주세요.")
    private String memberName;

    @NotBlank(message = "이메일은 필수 입력값 입니다.")
    @Email(message = "올바른 이메일 주소를 입력하세요.")
    private String memberEmail;

    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String memberPassword;

    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String reMemberPassword;

    @NotBlank(message = "휴대폰번호는 필수 입력값 입니다.")
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String memberPhoneNumber;

    @NotBlank(message = "우편번호는 필수 입력값 입니다.")
    private String addressZipcode;

    @NotBlank(message = "도로명은 필수 입력값 입니다.")
    private String addressRoad;

    @NotBlank(message = "상세주소는 필수 입력값 입니다.")
    private String addressContent;
}
