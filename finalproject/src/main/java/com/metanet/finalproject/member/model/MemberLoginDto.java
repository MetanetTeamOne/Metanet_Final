package com.metanet.finalproject.member.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberLoginDto {
    @NotBlank
    private String memberEmail;

    @NotBlank
    private String memberPassword;
}
