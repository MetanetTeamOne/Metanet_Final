package com.metanet.finalproject.reply.service;

import com.metanet.finalproject.reply.model.Reply;

public interface IReplyService {
	// 관리자 답변 등록
	void insertReplyToMemhelp(Reply reply);
		
	// 관리자가 답변 강제 삭제
	void deleteMemhelp(int memHelpNum);
		
	// 문의한 일반 유저가 관리자의 답변을 확인할 수 있게 하기위해 조회하는 함수
	Reply searchReplyOfMemhelp(int memHelpNum);
}
