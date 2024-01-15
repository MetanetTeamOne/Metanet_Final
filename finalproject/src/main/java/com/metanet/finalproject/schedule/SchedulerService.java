package com.metanet.finalproject.schedule;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.repository.IMemberRepository;
import com.metanet.finalproject.pay.model.request.SubscribePayload;
import com.metanet.finalproject.pay.model.request.User;
import com.metanet.finalproject.pay.service.Bootpay;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpResponse;
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
    
 //   @Scheduled(cron = "0 46 22 * * ?") //매월 1일 9시 00분에 동작. 구독 결제 진행
    @Scheduled(cron = "0 0 9 1 * ?") //매월 1일 9시 00분에 동작. 구독 결제 진행
    private void subscribeCard(){
        log.info("정기 구독을 진행합니다.");
        List<Member> memberList = memberRepository.getMemberList();
        for (Member member : memberList) {
            String memberSubscribe = member.getMemberSubscribe();
            if (memberSubscribe.equals("1")) {
   			 try {
   				 Bootpay bootpay = new Bootpay("65a160cc00c78a001d3460da", "w+lPLe/uLDoPOUXZxesF1bNUGpWPKgQ5n0dUtFmxwW8=");
            	 bootpay.getAccessToken();
   				 SubscribePayload payload = new SubscribePayload();
            	 payload.billingKey = member.getMemberCard();
            	 payload.orderId = "자동결제"+member.getMemberId()+new java.util.Date();
            	 payload.itemName = "자동결제";
            	 payload.price = 100;
            	 payload.userInfo = new User();
            	 payload.userInfo.id =Integer.toString(member.getMemberId());
            	 payload.userInfo.username = member.getMemberName();
            	 payload.userInfo.email = member.getMemberEmail();
            	 payload.userInfo.phone = member.getMemberPhoneNumber();

        		HttpResponse response = bootpay.requestSubscribe(payload);
			 	int statusCode = response.getStatusLine().getStatusCode();

        	   if(statusCode == 200) { //success
        	       System.out.println("requestSubscribe success: " + response);
        	   } else {
        	       System.out.println("requestSubscribe false: " + response);
        	   }
        	} catch (Exception e) {
        	   e.printStackTrace();
        	}	

            }
        }
    }
}
