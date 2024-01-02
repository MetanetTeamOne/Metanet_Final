package com.metanet.finalproject.member.service;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.model.MemberUpdateDto;

import java.util.List;

public interface IMemberService {
    public Member getMember(int memberId);
    public List<Member> getMemberList();
    public void insertMember(Member member);
    public void updateMember(MemberUpdateDto member, String email);
    public void deleteMember(String password);
}
