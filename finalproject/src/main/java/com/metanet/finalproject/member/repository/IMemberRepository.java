package com.metanet.finalproject.member.repository;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.model.MemberUpdateDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface IMemberRepository {
    public Member getMember(@Param("memberId") int memberId);
    public List<Member> getMemberList();
    public int insertMember(@Param("member") Member member);
    public void updateMember(@Param("member") Member member, @Param("email") String email);
    public void deleteMember(@Param("password") String password);
	public int getMemberId(@Param("email") String email);
    
    //이메일로 특정 회원조회
  	Member selectMember(@Param("memberEmail") String memberEmail);
    //구독신청(memberSubscribe를 1로, memberSubscribeDate를 sysdate로 update), 기본값 0, 기본값 2000-01-01
  	void insertSubscribe(Member member);
  	//구독해지(memberSubscribe를 1 -> 0으로 update)
  	void updateSubscribe(Member member);
  	//구독 상태 조회
  	String selectSubscribe(@Param("memberEmail") String memberEmail);
  	//카드등록(등록상태 : 0,1) -> 카드등록상태가 1이면 구독 신청 가능, 기본값 0
  	void insertCard(@Param("memberEmail") String memberEmail);


}
