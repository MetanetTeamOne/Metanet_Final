package com.metanet.finalproject.memhelp.service;

import java.util.List;

import com.metanet.finalproject.memhelp.model.Memhelp;
import com.metanet.finalproject.memhelp.model.MemhelpSearchByMemberId;

public interface IMemhelpService {
	List<Memhelp> searchAllMemhelp();
	// 회원별 문의사항 전체조회
	List<MemhelpSearchByMemberId> searchMemhelp(int memberId);
	
	// 회원별 문의사항 등록
	void insertMemhelp(Memhelp memhelp);
}
