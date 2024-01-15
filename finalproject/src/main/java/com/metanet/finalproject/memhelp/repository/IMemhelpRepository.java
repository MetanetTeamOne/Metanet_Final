package com.metanet.finalproject.memhelp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.memhelp.model.Memhelp;
import com.metanet.finalproject.memhelp.model.MemhelpSearchByMemberId;

@Repository 
@Mapper
public interface IMemhelpRepository {
	// 모든 회원 문의사항 전체조회(관리자)
	List<Memhelp> searchAllMemhelp();
	
	// 문의사항 번호에 해당하는 문의사항 조회(관리자);
	Memhelp searchMemhelpByMemhelpIdOnly(int memHelpNum);
	
	// 관리자가 답변 등록시 문의사항 상태 업데이트
	void updateStateOfMemhelp(int memHelpNum);
	
	// 회원별 문의사항 전체조회
	List<MemhelpSearchByMemberId> searchMemhelp(int memberId);
	
	// 회원별 문의사항 등록
	void insertMemhelp(Memhelp memhelp);
	
	// 회원이 선택한 문의사항 조회 -> 관리자 답변시 관리자 답변도 보이도록
	Memhelp searchMemhelpByMemhelpId(@Param("memHelpNum") int memHelpNum, @Param("memberId") int memberId);


	int getMemHelpCount(int memberId);

	List<MemhelpSearchByMemberId> searchPagingMemhelp(@Param("start") int firstRecordIndex, @Param("end") int lastRecordIndex, @Param("memberId") int memberId);
	
	// 문의 개수(상태값에 따라 >> 대기 : 0, 완료 : 1)
	int countMemHelp(@Param("memHelpState") String memHelpState);

	List<Memhelp> searchPagingAllMemhelp(@Param("start") int start, @Param("end") int end);

	int getAdminMemHelpCount();
}
