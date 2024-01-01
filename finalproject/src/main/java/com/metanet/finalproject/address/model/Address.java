package com.metanet.finalproject.address.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Address {
	private int addressId;
	private String addressZipcode;
	private String addressRoad;
	private String addressContent;
	private String addressCategory;
	private String addressDetail;
	private int memberId;
}
