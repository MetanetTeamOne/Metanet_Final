package com.metanet.finalproject.orders.repository;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.orders.model.Orders;

@Repository
@Mapper
public interface IOrdersRepository {
	// 회원
	List<Orders> searchOrder(int memberId);
	List<Orders> searchOrder(int memberId, int washId); 
	List<Orders> searchOrderId(int washId); 
	List<Orders> searchMonthOrder(int memberId, int month);
	void insertOrder(Orders orders);
	void updateOrder(Orders orders);
	void deleteOrder(int ordersId, int washId);
	int countOrder(int memberId); //회원별 주문 건수 추가
	 
	// 관리자
	int countOrder(); //전체 주문 건수 추가
	int countNewOrder(); //10분마다 새로운 주문 건수 조회를 위해 추가
	List<Orders> searchOrdersList();
	List<Orders> searchOrdersList(Orders orders);
}
