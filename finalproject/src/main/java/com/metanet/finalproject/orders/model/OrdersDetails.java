package com.metanet.finalproject.orders.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class OrdersDetails {
	private int washId;
	private int ordersTotalCount;
	private int ordersTotalPrice;
	private java.sql.Date ordersDate;
	private String orderState;
}
