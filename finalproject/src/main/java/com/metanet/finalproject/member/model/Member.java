package com.metanet.finalproject.member.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Member {
	private int memberId;
	private String memberName;
	private String memberEmail;
	private String memberPhoneNumber;
	private String memberPassword;
	private Date memberJoinDate;
	private String memberJoinState;
	private String memberSubscribe;
	private Date memberSubscribeDate;
	private String memberCard;
}
