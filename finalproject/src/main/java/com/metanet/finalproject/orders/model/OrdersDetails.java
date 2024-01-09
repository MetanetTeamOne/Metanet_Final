package com.metanet.finalproject.orders.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class OrdersDetails {
	private int washId;
	private int ordersTotalCount;
	private int ordersTotalPrice;
	private Date ordersDate;
	private String orderState;
}
