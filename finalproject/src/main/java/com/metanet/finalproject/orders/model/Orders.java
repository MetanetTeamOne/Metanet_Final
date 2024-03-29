package com.metanet.finalproject.orders.model;

import java.sql.Date;

import lombok.Data;
import lombok.ToString;

@Data @ToString
public class Orders {
	// 주문 ID
	private int ordersId;

	// 세탁 ID
	private int washId;

	// 수량
	private int ordersCount;

	// 금액
	private int ordersPrice;

	// 주문날짜
	private Date ordersDate;

	// 요청사항
	private String ordersComment;

	// 진행상태
	private String ordersStatus;

	// 세탁확인
	private String ordersCheckDate;

	// 유저 ID
	private int memberId;

	// 세탁 ID
	private int laundryId;

	// 이미지 경로
	private String ordersDirPath;

	// 이미지 데이터
	// private byte[] ordersImageData;

}
