package com.metanet.finalproject.orders.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.model.OrdersDetails;
import com.metanet.finalproject.orders.repository.IOrdersRepository;

@Service
public class OrdersService implements IOrdersService{
	@Autowired
	IOrdersRepository ordersRepository;
	
	// 회원
	@Override
	public List<Orders> searchOrder(int memberId) {
		return ordersRepository.searchOrder(memberId);
	}

	@Override
	public List<Orders> searchOrder(int memberId, int washId) {
		return ordersRepository.searchOrder(memberId, washId);
	}

	
	@Override
	public List<OrdersDetails> searchMemOrder(int memberId) {
		return ordersRepository.searchMemOrder(memberId);
	}
	
	@Override
	public List<OrdersDetails> searchMonthOrder(int memberId, int month) {
		return ordersRepository.searchMonthOrder(memberId, month);
	}
	
	@Override
	public void insertOrder(Orders orders) {
		ordersRepository.insertOrder(orders);
	}

	@Override
	public void updateOrder(Orders orders) {
		ordersRepository.updateOrder(orders);
	}

	@Override
	public void deleteOrder(int ordersId, int washId) {
		ordersRepository.deleteOrder(ordersId, washId);
	}
	
	@Override
	public void deleteWashOrder(int washId) {
		ordersRepository.deleteWashOrder(washId);
	}
	
	@Override
	public int countOrder(int memberId) {
		return ordersRepository.countOrder(memberId);
	}
	
	// 관리자
	@Override
	public List<Orders> searchOrdersList() {
		return ordersRepository.searchOrdersList();
	}

	@Override
	public List<Orders> searchOrdersList(Orders orders) {
		return ordersRepository.searchOrdersList(orders);
	}


	@Override
	public List<Orders> searchOrderId(int washId) {
		return ordersRepository.searchOrderId(washId);
	}

	@Override
	public int countOrder() {
		return ordersRepository.countOrder();
	}

//	@Override
//	public int countNewOrder(Date ordersDate) {
//		return ordersRepository.countNewOrder(ordersDate);
//	}
	

	@Override
	public int searchMaxWashId() {
		return ordersRepository.searchMaxWashId();
	}

	@Override
	public void updateStatus(int washId, String ordersStatus) {
		ordersRepository.updateStatus(washId, ordersStatus);
	}

	@Override
	public int getOrderCount(int month, int memberId) {
		return ordersRepository.getOrderCount(month, memberId);
	}

	@Override
	public int getOrderCount() {
		return ordersRepository.getOrderCount();
	}

	@Override
	public List<OrdersDetails> searchPagingMemOrder(int firstRecordIndex, int lastRecordIndex, int memberId) {
		return ordersRepository.searchPagingMemOrder(firstRecordIndex, lastRecordIndex, memberId);
	}

	@Override
	public List<OrdersDetails> searchPagingMemMonthOrder(int firstRecordIndex, int lastRecordIndex, int memberId, int month) {
		return ordersRepository.searchPagingMemMonthOrder(firstRecordIndex, lastRecordIndex, memberId, month);
	}

	@Override
	public List<Orders> searchPagingOrdersList(int start, int end) {
		return ordersRepository.searchPagingOrdersList(start, end);
	}


}
