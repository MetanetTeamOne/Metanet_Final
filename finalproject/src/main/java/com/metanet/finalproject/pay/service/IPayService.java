package com.metanet.finalproject.pay.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.metanet.finalproject.pay.model.Pay;

public interface IPayService {
	List<Pay> getMemberPay(int memberId);
	//결제 정보 상세 조회
	Pay getPay(int payId);
	//결제 상태 검색
	List<Pay> getPayState(String payState);
	//결제 진행
	void insertPay(Pay pay);
	
}
