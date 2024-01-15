package com.metanet.finalproject.memhelp.service;

import java.util.List;

import com.metanet.finalproject.memhelp.model.Memhelp;
import com.metanet.finalproject.memhelp.model.MemhelpSearchByMemberId;

public interface IMemhelpService {
	List<Memhelp> searchAllMemhelp();
	// 문의사항 번호에 해당하는 문의사항 조회(관리자);
	Memhelp searchMemhelpByMemhelpIdOnly(int memHelpNum);
	// 회원별 문의사항 전체조회
	List<MemhelpSearchByMemberId> searchMemhelp(int memberId);
	
	// 관리자가 답변 등록시 문의사항 상태 업데이트
	void updateStateOfMemhelp(int memHelpNum);
	
	// 회원별 문의사항 등록
	void insertMemhelp(Memhelp memhelp);
	
	// 회원이 선택한 문의사항 조회 -> 관리자 답변시 관리자 답변도 보이도록
	Memhelp searchMemhelpByMemhelpId(int memHelpNum, int memberId);

    int getMemHelpCount(int memberId);

	List<MemhelpSearchByMemberId> searchPagingMemhelp(int firstRecordIndex, int lastRecordIndex, int memberId);

	int countMemHelp(String memHelpState);

	List<Memhelp> searchPagingAllMemhelp(int start, int end);

	int getAdminMemHelpCount();
}
