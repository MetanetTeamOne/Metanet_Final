package com.metanet.finalproject.address.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Address {
	private int addressId;

	@NotBlank(message = "우편번호는 필수 입력값 입니다.")
	private String addressZipcode;

	@NotBlank(message = "도로명은 필수 입력값 입니다.")
	private String addressRoad;

	@NotBlank(message = "상세주소는 필수 입력값 입니다.")
	private String addressContent;
	private String addressCategory;
	private String addressDetail;
	private int memberId;
}
