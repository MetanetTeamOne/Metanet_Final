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
	
	public List<Pay> getPagingMemberPay(int firstRecordIndex, int lastRecordIndex, int memberId) {
		return payRepository.getPagingMemberPay(firstRecordIndex, lastRecordIndex, memberId);
	}

	@Override
	public int getPayCount(int memberId) {
		return payRepository.getPayCount(memberId);
	}

	@Override
	public int getPayCount() {
		return payRepository.getPayCount();
	}

	@Override
	public List<Pay> getPagingMemberPayByState(int firstRecordIndex, int lastRecordIndex, int memberId, String state) {
		return payRepository.getPagingMemberPayByState(firstRecordIndex, lastRecordIndex, memberId, state);
	}

	@Override
	public int getPayCountByState(int memberId, String state) {
		return payRepository.getPayCountByState(memberId, state);
	}

	@Override
	public int getPayCountByState(String state) {
		return payRepository.getPayCountByState(state);
	}

	@Override
	public List<Pay> getPagingPay(int start, int end) {
		return payRepository.getPagingPay(start, end);
	}

	@Override
	public List<Pay> getPagingPayState(int start, int end, String payState) {
		return payRepository.getPagingPayState(start, end, payState);
	}

	@Override
	public int getPayAllCountByState(String payState) {
		return payRepository.getPayAllCountByState(payState);
	}

}
