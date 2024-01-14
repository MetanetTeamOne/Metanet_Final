package com.metanet.finalproject.pay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.finalproject.pay.model.Pay;
import com.metanet.finalproject.pay.repository.IPayRepository;

@Service
public class PayService implements IPayService{
	
	@Autowired
	IPayRepository payRepository;

	@Override
	public Pay getPay(int payId) {
		return payRepository.getPay(payId);
	}

	@Override
	public List<Pay> getPayState(String payState, int membrId) {
		return payRepository.getPayState(payState, membrId);
	}
	
	@Override
	public void insertPay(Pay pay) {
		payRepository.insertPay(pay);
	}

	@Override
	public List<Pay> getMemberPay(int memberId) {
		return payRepository.getMemberPay(memberId);
	}

	@Override
	public void updatePay(Pay pay) {
		payRepository.updatePay(pay);
	}

	@Override
	public List<Pay> getPay() {
		return payRepository.getPay();
	}

	@Override
	public List<Pay> getPayState(String payState) {
		return payRepository.getPayState(payState);
	}

	@Override
	public int sumPay(int payMoney) {
		return payRepository.sumPay(payMoney);
	}

	@Override
	public Pay getWashIdPay(int washId) {
		return payRepository.getWashIdPay(washId);
	}

}
