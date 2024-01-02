package com.metanet.finalproject.orders.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.metanet.finalproject.orders.model.Orders;

public interface IOrdersService {
	// 회원
	List<Orders> searchOrder(int memberId);
	List<Orders> searchOrder(int memberId, int washId); 
	void insertOrder(Orders orders);
	void updateOrder(Orders orders);
	void deleteOrder(Orders orders);
		
	// 관리자
	List<Orders> searchOrdersList();
	List<Orders> searchOrdersList(Orders orders);
}
