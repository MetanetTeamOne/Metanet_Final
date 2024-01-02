package com.metanet.finalproject.orders.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.orders.model.Orders;

@Repository
@Mapper
public interface IOrdersRepository {
	// 회원
	List<Orders> searchOrder(@Param("memberId") int memberId);
	List<Orders> searchOrder(@Param("memberId") int memberId, @Param("washId") int washId); 
	void insertOrder(Orders orders);
	void updateOrder(Orders orders);
	void deleteOrder(Orders orders);
	
	// 관리자
	List<Orders> searchOrdersList();
	List<Orders> searchOrdersList(Orders orders);
}
