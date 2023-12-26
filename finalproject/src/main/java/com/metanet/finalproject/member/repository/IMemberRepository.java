package com.metanet.finalproject.member.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Repository;

import com.metanet.finalproject.member.model.MemberDto;

@Repository
@Mapper
public interface IMemberRepository {
	MemberDto findStudentById(@Param("memberId") String memberId);
	void insertStudent(@Param("member") MemberDto member);
	void deleteStudent(@Param("email") String email);
	void updateStudent(@Param("member") MemberDto member);
	MemberDto getStudentInfo(@Param("memberId") String memberId);
	String getPassword(@Param("memberId") String memberId);
	
}
