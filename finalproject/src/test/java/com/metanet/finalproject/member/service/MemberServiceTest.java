package com.metanet.finalproject.member.service;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.model.MemberUpdateDto;
import com.metanet.finalproject.member.repository.IMemberRepository;
import com.metanet.finalproject.role.model.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberServiceTest {

    @Autowired
    IMemberRepository memberRepository;

    @Test
    public void getMember(){
        assertThat(memberRepository.getMember(1).getMemberName()).isEqualTo("최가인");
    }

    @Test
    public void getAllMember(){
        assertThat(memberRepository.getMemberList().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void deleteMember(){
        memberRepository.deleteMember("password123");
//        assertThat(memberRepository.getMember(1).getMemberName()).isEqualTo("최가인");
        assertThat(memberRepository.getMember(1)).isNull();
//        assertThrows(NullPointerException.class, () -> memberRepository.getMember(1));
    }

    @Test
    @Transactional
    public void updateMember(){

        Member member = new Member();
        member.setMemberName("최가인인");
        member.setMemberPhoneNumber("010-0000-0000");
        member.setMemberPassword("123123123");
        memberRepository.updateMember(member, "chlrkdls1269@gmail.com");

        assertThat(memberRepository.getMember(1).getMemberPassword()).isEqualTo("123123123");
        assertThat(memberRepository.getMember(1).getMemberPhoneNumber()).isEqualTo("010-0000-0000");
    }

    @Test
    @Transactional
    public void insertMember(){

        Member member = new Member();
        member.setMemberName("최동연");
        member.setMemberEmail("xxx@naver.com");
        member.setMemberPhoneNumber("010-1234-1234");
        member.setMemberPassword("1234");
        member.setMemberJoinState("1");
        member.setMemberSubscribe("0");
        member.setMemberSubscribeDate(new Date(0));
        member.setMemberCard("0");



        int id = memberRepository.insertMember(member);
        assertThat(id).isEqualTo(7);
        assertThat(memberRepository.getMemberList().size()).isEqualTo(2);
        assertThat(memberRepository.getMemberList().get(1).getMemberName()).isEqualTo("최동연");
//        assertThat(memberRepository.getMember(2).getMemberName()).isEqualTo("최동연");
    }

    @Test
    @Transactional
    public void selectMember(){
        String email = "email@com22333";
        Member member = memberRepository.selectMember(email);
        assertThat(member).isNull();
    }
}