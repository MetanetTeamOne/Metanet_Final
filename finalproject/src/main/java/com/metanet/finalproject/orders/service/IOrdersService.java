package com.metanet.finalproject.orders.service;

import java.util.List;

import com.metanet.finalproject.orders.model.Orders;

public interface IOrdersService {
	// 회원
	List<Orders> searchOrder(int memberId);
	List<Orders> searchOrder(int memberId, int washId);
	List<Orders> searchOrderId(int ordersId); 

	void insertOrder(Orders orders);
	void updateOrder(Orders orders);
	void deleteOrder(int ordersId, int washId);
		
	// 관리자
	List<Orders> searchOrdersList();
	List<Orders> searchOrdersList(Orders orders);
}
