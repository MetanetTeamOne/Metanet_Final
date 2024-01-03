package com.metanet.finalproject.member.model;

import lombok.Data;

@Data
public class MemberInsertDto {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
}
