package com.metanet.finalproject.laundry.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Laundry {
	private int laundryId;     
	private String laundryName;
	private String laundryCategory;
	private int laundryPrice;      
	private int laundryCategoryId;
}
