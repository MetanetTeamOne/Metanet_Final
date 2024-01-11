package com.metanet.finalproject.pay.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Pay {

	private int payId;
	private int payDelivery;
	private int payMoney;
	private Date payDate;
	private String payState;
	private int washId;
	private int memberId;
	
}
