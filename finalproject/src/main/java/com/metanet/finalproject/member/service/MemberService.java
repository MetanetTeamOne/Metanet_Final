package com.metanet.finalproject.member.service;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.model.MemberUpdateDto;
import com.metanet.finalproject.member.repository.IMemberRepository;
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
    public void updateMember(MemberUpdateDto member, String email) {
        memberRepository.updateMember(member, email);
    }

    @Override
    @Transactional
    public void deleteMember(String password) {
        memberRepository.deleteMember(password);
    }
}
