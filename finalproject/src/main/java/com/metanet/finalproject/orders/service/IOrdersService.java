package com.metanet.finalproject.orders.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.metanet.finalproject.orders.model.Orders;

public interface IOrdersService {
	// 회원
		List<Orders> searchOrder(@Param("memberId") int memberId);
		List<Orders> searchOrder(@Param("memberId") int memberId, @Param("washId") int washId); 
		String insertOrder(@Param("orders") Orders orders);
		String updateOrder(@Param("orders") Orders orders);
		String deleteOrder(@Param("orders") Orders orders);
		
		// 관리자
		List<Orders> searchOrdersList();
		List<Orders> searchOrdersList(@Param("ordersDate") Orders orders);
}
