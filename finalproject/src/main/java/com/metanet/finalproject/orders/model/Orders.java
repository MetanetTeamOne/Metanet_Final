package com.metanet.finalproject.orders.model;

import java.sql.Date;

import lombok.Data;

@Data
public class Orders {
	// 주문 ID
	private int ordersId;
	
	// 세탁 ID
	private int wishId;
	
	// 수량
	private int ordersCount;
	
	// 금액
	private int ordersPrice;
	
	// 주문날짜
	private Date  ordersDate;
	
	// 요청사항
	private String ordersComment;
	
	// 진행상태
	private String ordersStatus;
}
