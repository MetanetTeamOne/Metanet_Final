package com.metanet.finalproject.pay.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.pay.model.Pay;

@Repository
@Mapper
public interface IPayRepository {
	List<Pay> getMemberPay(@Param("memberId") int memberId);
	//결제 정보 상세 조회
	Pay getPay(@Param("payId") int payId);
	
	List<Pay> getPay();

	//결제 상태 검색
	List<Pay> getPayState(@Param("payState") String payState, @Param("memberId") int memberId);
	List<Pay> getPayState(@Param("payState") String payState);
	//결제 진행
	void insertPay(Pay pay);
	//결제 업데이트
	void updatePay(Pay pay);
	
	// 결제 총액
	int sumPay(@Param("payMoney") int payMoney);
}
