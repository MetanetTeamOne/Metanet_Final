package com.metanet.finalproject.orders.service;

import java.util.List;

import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.model.OrdersDetails;

public interface IOrdersService {
	// 회원
	List<Orders> searchOrder(int memberId);
	List<Orders> searchOrder(int memberId, int washId);
	List<Orders> searchOrderId(int washId); 
	
	List<OrdersDetails> searchMemOrder(int memberId);
	List<OrdersDetails> searchMonthOrder(int memberId, int month);
	int searchMaxWashId(int memberId);

	void insertOrder(Orders orders);
	void updateOrder(Orders orders);
	void deleteOrder(int ordersId, int washId);
	void deleteWashOrder(int washId);
	int countOrder(int memberId); //회원별 주문 건수 추가
	
	// 관리자
	int countOrder(); //전체 주문 건수 추가
	int countNewOrder(); //10분마다 새로운 주문 건수 조회를 위해 추가
	List<Orders> searchOrdersList();
	List<Orders> searchOrdersList(Orders orders);
	void updateStatus(int washId, String ordersStatus);
}
