package com.metanet.finalproject.orders.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class OrdersInsertList {
		private List<OrdersInsert> orderList;

		// 요청사항
		private String ordersComment;

		// 진행상태
		private String ordersStatus;
		
		// 세탁확인
		private String ordersCheckDate;

		// 이미지 데이터
		private byte[] ordersImageData;
}
