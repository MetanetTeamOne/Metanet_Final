package com.metanet.finalproject.orders.service;

import java.sql.Date;
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
	int searchMaxWashId();

	void insertOrder(Orders orders);
	void updateOrder(Orders orders);
	void deleteOrder(int ordersId, int washId);
	void deleteWashOrder(int washId);
	int countOrder(int memberId); //회원별 주문 건수 추가
	
	// 관리자
	int countOrder(); //전체 주문 건수 추가
	//10분마다 새로운 주문 건수 조회를 위해 추가
	//int countNewOrder(Date ordersDate);
	List<Orders> searchOrdersList();
	List<Orders> searchOrdersList(Orders orders);
	void updateStatus(int washId, String ordersStatus);

    int getOrderCount(int month, int memberId);
    int getOrderCount();

	List<OrdersDetails> searchPagingMemOrder(int firstRecordIndex, int lastRecordIndex, int memberId);

	List<OrdersDetails> searchPagingMemMonthOrder(int firstRecordIndex, int lastRecordIndex, int memberId, int month);

	List<Orders> searchPagingOrdersList(int start, int end);
}
