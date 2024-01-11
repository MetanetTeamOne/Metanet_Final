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
	// 회원별 문의사항 전체조회
	List<MemhelpSearchByMemberId> searchMemhelp(int memberId);
	
	// 회원별 문의사항 등록
	void insertMemhelp(Memhelp memhelp);
}
