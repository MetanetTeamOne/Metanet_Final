package com.metanet.finalproject.reply.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.metanet.finalproject.reply.model.Reply;
import com.metanet.finalproject.reply.repository.IReplyRepository;

@Transactional
@Service
public class ReplyService implements IReplyService{
	
	@Autowired
	IReplyRepository replyRepository;
	
	@Override
	public void insertReplyToMemhelp(Reply reply) {
		replyRepository.insertReplyToMemhelp(reply);
	}

	@Override
	public void deleteMemhelp(int memHelpNum) {
		replyRepository.deleteMemhelp(memHelpNum);
	}

	@Override
	public Reply searchReplyOfMemhelp(int memHelpNum) {
		return replyRepository.searchReplyOfMemhelp(memHelpNum);
	}

}
