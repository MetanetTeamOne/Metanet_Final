package com.metanet.finalproject.member.service;

import java.util.List;

import com.metanet.finalproject.member.model.Member;

public interface IMemberService {
    public Member getMember(int memberId);
    public List<Member> getMemberList();
    public void insertMember(Member member);
    public void updateMember(Member member, String email);
    public void deleteMember(String memberEmail, String memberJoinState);
	public int getMemberId(String email);
    //이메일로 특정 회원조회
  	Member selectMember(String memberEmail);

    // 구독신청 -> 구독o : 1
 	void insertSubscribe(Member member);
 	// 구독변경 -> 구독x : 0, 
 	void updateSubscribe(Member member);
 	//구독 상태 조회
 	String selectSubscribe(String memberEmail);
 	// 카드등록(등록상태 : 0,1) -> 카드등록상태가 1이면 구독 신청 가능
 	void insertCard(String memberEmail);
}
