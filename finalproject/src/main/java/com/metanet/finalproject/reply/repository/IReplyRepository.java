package com.metanet.finalproject.reply.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.reply.model.Reply;

@Repository
@Mapper
public interface IReplyRepository {
	// 관리자 답변 등록
	void insertReplyToMemhelp(Reply reply);
	
	// 관리자가 답변 강제 삭제
	void deleteMemhelp(int memHelpNum);
	
	// 문의한 일반 유저가 관리자의 답변을 확인할 수 있게 하기위해 조회하는 함수
	Reply searchReplyOfMemhelp(int memHelpNum);
}
