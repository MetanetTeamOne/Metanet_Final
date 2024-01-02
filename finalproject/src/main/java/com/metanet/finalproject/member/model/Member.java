package com.metanet.finalproject.member.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Member {
	private String memberEmail;
	private String memberName;
	private String memberPhoneNumber;
	private String memberPassword;
	private String memberSex;
	private String memberAge;
	private Date memberJoinDate;
	private String memberAddress;
	private String memberState;
	private Date memberBanState;
	private int memberPoint;
}
