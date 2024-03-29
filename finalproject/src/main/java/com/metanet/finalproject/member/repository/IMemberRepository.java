package com.metanet.finalproject.member.repository;

import java.util.List;

import com.metanet.finalproject.paging.Pagination;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.metanet.finalproject.member.model.Member;

@Repository
@Mapper
public interface IMemberRepository {
    public Member getMember(@Param("memberId") int memberId);
    public List<Member> getMemberList();

    public void insertMember(@Param("member") Member member);
    public void updateMember(@Param("member") Member member, @Param("email") String email);
    public void deleteMember(@Param("memberEmail") String memberEmail, @Param("state") String memberJoinState);

	public void deleteWithDrawMember(@Param("memberId") int memberId);
	public int getMemberId(@Param("email") String email);
	public List<Member> getPagingMemberListByState(@Param("start") int start, @Param("end") int end, @Param("state") String state);
	public List<Member> getPagingMemberList(Pagination pagination);
	public int getMemberCount(String state);

    
    //이메일로 특정 회원조회
  	Member selectMember(@Param("memberEmail") String memberEmail);
  	//전화번호로 특정 회원조회(소셜로그인 구현용)
  	Member searchMemberByPhonenumber(@Param("memberPhonenumber") String memberPhonenumber);
    //구독신청(memberSubscribe를 1로, memberSubscribeDate를 sysdate로 update), 기본값 0, 기본값 2000-01-01
  	void insertSubscribe(Member member);
  	//구독해지(memberSubscribe를 1 -> 0으로 update)
  	void updateSubscribe(Member member);
  	//구독 상태 조회
  	String selectSubscribe(@Param("memberEmail") String memberEmail);
  	//카드등록(등록상태 : 0,1) -> 카드등록상태가 1이면 구독 신청 가능, 기본값 0
  	void insertCard(@Param("memberEmail") String memberEmail);
  	void deleteCard(@Param("memberEmail") String memberEmail);
  	
  	int getPhoneCount(@Param("memberPhoneNumber") String memberPhoneNumber);

}
