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
    public void insertMember(@Param("member") Member member);
    public void updateMember(@Param("member") MemberUpdateDto member, @Param("email") String email);
    public void deleteMember(@Param("password") String password);



}
