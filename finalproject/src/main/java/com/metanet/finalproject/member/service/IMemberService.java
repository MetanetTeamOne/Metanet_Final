package com.metanet.finalproject.member.service;

import java.util.List;

import com.metanet.finalproject.member.model.MemberDto;

public interface IMemberService {
	
	public MemberDto findStudentById(String memberId);
	
	public void insertStudent(MemberDto member);
	
	public void deleteStudent(String email);
	
	public void updateStudent(MemberDto member);
	
	public MemberDto getStudentInfo(String memberId);
	
	public String getPassword(String memberId);

}
