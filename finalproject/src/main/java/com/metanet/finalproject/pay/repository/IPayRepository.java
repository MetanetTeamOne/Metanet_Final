package com.metanet.finalproject.pay.repository;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.pay.model.Pay;

@Repository
@Mapper
public interface IPayRepository {
	List<Pay> getMemberPay(@Param("memberId") int memberId);

	// 결제 정보 상세 조회
	Pay getPay(@Param("payId") int payId);

	Pay getWashIdPay(@Param("washId") int washId);

	List<Pay> getPay();

	// 결제 상태 검색
	List<Pay> getPayState(@Param("payState") String payState, @Param("memberId") int memberId);

	List<Pay> getPayState(@Param("payState") String payState);

	// 결제 진행
	void insertPay(Pay pay);

	// 결제 업데이트
	void updatePay(Pay pay);

	// 결제 총액
	int sumPay();

	// 월별 결제 총액
	int sumMonthPay(@Param("payDate") Date payDate);

	List<Pay> getPagingMemberPay(@Param("start") int firstRecordIndex, @Param("end") int lastRecordIndex,
			@Param("memberId") int memberId);

	int getPayCount(int memberId);

	int getPayCount();

	List<Pay> getPagingMemberPayByState(@Param("start") int firstRecordIndex, @Param("end") int lastRecordIndex,
			@Param("memberId") int memberId, @Param("state") String state);

	int getPayCountByState(@Param("memberId") int memberId, @Param("state") String state);

	int getPayCountByState(String state);

	List<Pay> getPagingPay(@Param("start") int start, @Param("end") int end);

	List<Pay> getPagingPayState(@Param("start") int start, @Param("end") int end, @Param("state") String state);

	int getPayAllCountByState(String state);
}
