package com.metanet.finalproject.member.model;

import lombok.Data;

@Data
public class MemberInsertDto {
    private String memberName;
    private String memberEmail;
    private String memberPassword;
    private String memberPhoneNumber;
    private String addressZipcode;
    private String addressRoad;
    private String addressContent;
}
