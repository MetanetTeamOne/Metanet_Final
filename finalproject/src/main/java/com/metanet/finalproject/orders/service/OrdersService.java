package com.metanet.finalproject.orders.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.finalproject.orders.model.Orders;
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
	public List<Orders> searchOrderId(int ordersId) {
		return ordersRepository.searchOrderId(ordersId);
	}
}
