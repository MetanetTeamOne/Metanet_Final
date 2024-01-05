//package com.metanet.finalproject.member.service;
//
//import com.metanet.finalproject.member.model.Member;
//import com.metanet.finalproject.member.repository.IMemberRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Date;
//
//
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootTest
//class MemberServiceReTest {
//
//    @Autowired
//    IMemberService memberService;
//
//    @Autowired
//    IMemberRepository memberRepository;
//
//    @Test
//    @Transactional
//    public void insert(){
//        Member member = new Member();
//        member.setMemberName("최동연");
//        member.setMemberEmail("xx22x@naver.com");
//        member.setMemberPhoneNumber("010-1234-1234");
//        member.setMemberPassword("1234");
//        member.setMemberJoinState("1");
//        member.setMemberSubscribe("0");
//        member.setMemberSubscribeDate(new Date(0));
//        member.setMemberCard("0");
//
//        memberRepository.insertMember(member);
//        int id = memberRepository.getMemberId("xx22x@naver.com");
//        Assertions.assertThat(id).isEqualTo(14);
//    }
//}