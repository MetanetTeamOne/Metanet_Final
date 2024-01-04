package com.metanet.finalproject.member.model;

import lombok.Data;

@Data
public class MemberUpdateDto extends Member{
    String newPassword;
    String memberPhoneNumber;
}
