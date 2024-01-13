package com.metanet.finalproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.service.MemberService;
import com.metanet.finalproject.orders.service.OrdersService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SceduleService {

	@Autowired
	OrdersService ordersService;

	@Autowired
	MemberService memberService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	// Header에서 Token으로 사용자 이메일 획득
	private String getTokenUserEmail(HttpServletRequest request) {
		log.info("이메일로 토큰 받는중...");
		String token = "";

		try {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					token = cookie.getValue();
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}

		return jwtTokenProvider.getUserId(token);
	}

	// 스프링 웹 소켓 이용하기. 스케줄러에서는 세션 못 읽어서 사용자 지정 스케줄링 불가능
	//@Scheduled(cron="*/10 * * * * *") >> 10초
	// @Scheduled(cron="0 */10 * * * *") >> 10분마다 실행
//	@Scheduled(cron="0 */10 * * * *")
//	public void subscribeDate() {
		
		// 세탁소 사장에게 10분마다 방문해야 할 집이 몇 군데인지 조회 
		
//		int count = ordersService.countNewOrder(); // 현재 시점에서 10분 이전 주문 개수 조회
//		System.out.println("count=============="+count);
		
		//세탁소 사장에게 주기적으로 수거해야 할 세탁물이 몇 개인지 조회시키기 + 목록
		//세션에 아이디 담아서 조회해야 함
//		Member member = memberService.selectMember(getTokenUserEmail(request));
//		Date subscribeDate = memberService.getMember(member.getMemberId()).getMemberSubscribeDate();
		//System.out.println("=====스케줄러=====");
		//System.out.println("구독일=============="+subscribeDate);
//	}

}
