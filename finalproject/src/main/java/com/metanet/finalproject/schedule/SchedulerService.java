package com.metanet.finalproject.schedule;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.repository.IMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class SchedulerService {

    @Autowired
    IMemberRepository memberRepository;

//    @Scheduled(cron = "*/10 * * * * *") // 매 10초마다 동작

    @Scheduled(cron = "0 0 0 1 * ?") //매월 1일 0시 00분에 동작. 이때마다 탈퇴한지 1년지난 회원들을 삭제. 유예기간을 둔 이유는 추후 탈퇴 요청을 해제할 수 있기 때문
    public void deleteMember(){
        log.info("탈퇴한 멤버를 삭제 합니다.");
        List<Member> memberList = memberRepository.getMemberList();
        for (Member member : memberList) {
//            log.info("멤버를 조회합니다.");
            Date memberJoinDate = member.getMemberJoinDate();
            LocalDate withdrawDate = memberJoinDate.toLocalDate();
            LocalDate currentDate = LocalDate.now();
            if (currentDate.isAfter(withdrawDate.plusYears(1))&&member.getMemberJoinState().equals("0")) {
                log.info("memberId {} 회원이 삭제 되었습니다. 삭제 날짜: {}", member.getMemberId(), currentDate);
                memberRepository.deleteWithDrawMember(member.getMemberId());
            }
        }
    }
}
