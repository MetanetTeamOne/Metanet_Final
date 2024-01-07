package com.metanet.finalproject.orders.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.orders.model.Orders;

@Repository
@Mapper
public interface IOrdersRepository {
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
