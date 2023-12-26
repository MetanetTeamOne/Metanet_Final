package com.metanet.finalproject.member.service;

import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metanet.finalproject.member.model.MemberDto;
import com.metanet.finalproject.member.repository.IMemberRepository;

@Service
public class MemberService implements IMemberService {
	@Autowired
	IMemberRepository memberRepository;
	private static Logger logger = LoggerFactory.getLogger(MemberService.class.getName());
	

	public MemberDto findStudentById(String memberId) {
		logger.info("서비스");
		logger.info("studentId {}",memberId);
		System.out.println("service" + memberId);
		return memberRepository.findStudentById(memberId);
	}
	
	public void insertStudent(MemberDto member) {
		memberRepository.insertStudent(member);
	}
	
	public void deleteStudent(String email) {
		memberRepository.deleteStudent(email);
	}
	
	public void updateStudent(MemberDto member) {
		logger.info("회원정보 수정중...");
		memberRepository.updateStudent(member);
	}

	@Override
	public MemberDto getStudentInfo(String memberId) {
		return memberRepository.getStudentInfo(memberId);
	}

	@Override
	public String getPassword(String memberId) {
		return memberRepository.getPassword(memberId);
	}

}
