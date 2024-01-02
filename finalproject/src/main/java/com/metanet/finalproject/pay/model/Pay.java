package com.metanet.finalproject.pay.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Pay {

	private int payId;
	private int payDelivery;
	private int payMoney;
	private Date payDate;
	private String payState;
	private int ordersId;
	
}
