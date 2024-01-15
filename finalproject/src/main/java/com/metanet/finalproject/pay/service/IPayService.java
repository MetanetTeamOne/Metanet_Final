package com.metanet.finalproject.pay.service;

import java.util.List;

import com.metanet.finalproject.pay.model.Pay;

public interface IPayService {
	List<Pay> getMemberPay(int memberId);

	// 결제 정보 상세 조회
	Pay getPay(int payId);
	Pay getWashIdPay(int washId);
	List<Pay> getPay();

	List<Pay> getPayState(String payState, int memberId);

	// 결제 상태 검색
	List<Pay> getPayState(String payState);

	// 결제 진행
	void insertPay(Pay pay);

	// 결제 업데이트
	void updatePay(Pay pay);

	// 결제 총액
	int sumPay(int payMoney);

	List<Pay> getPagingMemberPay(int firstRecordIndex, int lastRecordIndex, int memberId);

	int getPayCount(int memberId);
	int getPayCount();

	List<Pay> getPagingMemberPayByState(int firstRecordIndex, int lastRecordIndex, int memberId, String state);

	int getPayCountByState(int memberId, String state);
	int getPayCountByState(String state);

	List<Pay> getPagingPay(int start, int end);

	List<Pay> getPagingPayState(int start, int end, String payState);

	int getPayAllCountByState(String payState);
}
