package com.metanet.finalproject.member.service;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.repository.IMemberRepository;
import com.metanet.finalproject.paging.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberService implements IMemberService{
    @Autowired
    IMemberRepository memberRepository;

    @Override
    public Member getMember(int memberId) {
        return memberRepository.getMember(memberId);
    }
    
    @Override
	public Member searchMemberByPhonenumber(String memberPhonenumber) {
		return memberRepository.searchMemberByPhonenumber(memberPhonenumber);
	}

    @Override
    public List<Member> getMemberList() {
        return memberRepository.getMemberList();
    }


    @Override
    @Transactional
    public void insertMember(Member member) {
        memberRepository.insertMember(member);
    }

    @Override
    @Transactional
    public void updateMember(Member member, String email) {
        memberRepository.updateMember(member, email);
    }

    @Override
    @Transactional
    public void deleteMember(String memberEmail, String memberJoinState) {
        memberRepository.deleteMember(memberEmail, memberJoinState);
    }

    @Override
    @Transactional
    public int getMemberId(String email){
       return memberRepository.getMemberId(email);
    }

    @Override
    public List<Member> getPagingMemberListByState(int start, int end ,String state) {
        return memberRepository.getPagingMemberListByState(start, end, state);
    }

    @Override
    public List<Member> getPagingMemberList(Pagination pagination) {
        return memberRepository.getPagingMemberList(pagination);
    }

    @Override
    public int getMemberCount(String state) {
        return memberRepository.getMemberCount(state);
    }


    @Override
	public Member selectMember(String memberEmail) {
		return memberRepository.selectMember(memberEmail);
	}
    
    @Override
	public void insertSubscribe(Member member) {
		memberRepository.insertSubscribe(member);	}

	@Override
	public void updateSubscribe(Member member) {
		memberRepository.updateSubscribe(member);
	}

	@Override
	public String selectSubscribe(String memberEmail) {
		return memberRepository.selectSubscribe(memberEmail);
	}
	
	@Override
	public void insertCard(String memberEmail) {
		memberRepository.insertCard(memberEmail);
	}

	@Override
	public void deleteCard(String memberEmail) {
		memberRepository.deleteCard(memberEmail);
		
	}
}
