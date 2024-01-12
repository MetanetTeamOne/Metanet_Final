package com.metanet.finalproject.member.service;

import java.util.List;


import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.paging.Pagination;
import org.apache.ibatis.annotations.Param;

public interface IMemberService {
    public Member getMember(int memberId);
    public List<Member> getMemberList();
    public void insertMember(Member member);
    public void updateMember(Member member, String email);
    public void deleteMember(String memberEmail, String memberJoinState);
	public int getMemberId(String email);
	public List<Member> getPagingMemberListByState(Pagination pagination, String state);
	public List<Member> getPagingMemberList(Pagination pagination);
	public int getMemberCount();
    //이메일로 특정 회원조회
  	Member selectMember(String memberEmail);
  	Member searchMemberByPhonenumber(String memberPhonenumber);

    // 구독신청 -> 구독o : 1
 	void insertSubscribe(Member member);
 	// 구독변경 -> 구독x : 0, 
 	void updateSubscribe(Member member);
 	//구독 상태 조회
 	String selectSubscribe(String memberEmail);
 	// 카드등록(등록상태 : 0,1) -> 카드등록상태가 1이면 구독 신청 가능
 	void insertCard(String memberEmail);
  	void deleteCard(String memberEmail);

}
