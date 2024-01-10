package com.metanet.finalproject;

import java.util.Arrays;
import java.util.Optional;

import com.metanet.finalproject.role.repository.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.kakaocontroller.KakaoController;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.model.MemberLoginDto;
import com.metanet.finalproject.member.navercontroller.NaverController;
import com.metanet.finalproject.member.service.IMemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@Tag(name = "Login", description = "로그인 API")
public class LoginController {
	@Autowired
	IMemberService memberService;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	IRoleRepository roleRepository;

	// 로그인
	@Operation(summary = "로그인 view")
	@GetMapping("/logins")
	public String loginPage(Model model) {
		MemberLoginDto dto = new MemberLoginDto();
		model.addAttribute("dto", dto);
//        log.info("로그인 페이지...");
		return "member/login";
	}

	@Operation(summary = "로그인")
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("dto") MemberLoginDto loginMember, BindingResult result,
			HttpServletResponse response) {
		log.info("로그인 진행중...");
		if (result.hasErrors()) {
			return "member/login";
		}
//        log.info("email: {} password: {}", user.get("userid"), user.get("userpw"));
		Member member = memberService.selectMember(loginMember.getMemberEmail());
//        log.info("member: {}", member);

//		if (roleRepository.getRoleName(member.getMemberId()).equals("ROLE_ADMIN")) { 관리자 로그인 로직
//			if (member.getMemberPassword().equals(loginMember.getMemberPassword())) {
//				log.info("관리자 로그인 성공");
//				return "redirect:/admin";
//			} else {
//				result.rejectValue("memberPassword", null, "비밀번호가 일치하지 않습니다.");
//				return "member/login";
//			}
//		}
		if (member.getMemberJoinState().equals("0")) {
			log.info("탈퇴한 회원입니다.");
			result.rejectValue("memberEmail", null, "해당아이디는 탈퇴되었습니다.");
			return "member/login";
		}
		if (member == null) {
			log.info("계정이 존재하지 않음");
			result.rejectValue("memberEmail", null, "계정이 존재하지 않습니다.");
			return "member/login";
		}

		if (!passwordEncoder.matches(loginMember.getMemberPassword(), member.getMemberPassword())) {
			log.info("비밀번호가 일치하지 않음");
			result.rejectValue("memberPassword", null, "비밀번호가 일치하지 않습니다.");
			return "member/login";
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}

//        if (!(loginMember.getMemberPassword()).equals(member.getMemberPassword())) {
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }

		// 똑같은 로그인 화면에서 로그인 후
		// 본사와 지점은 admin 페이지로
		// 일반 회원은 home으로 가도록
		if (member.getMemberEmail().equals("king@king") || member.getMemberEmail().equals("admin@admin")) {
			return "redirect:/admin";
		}

		String token = jwtTokenProvider.generateToken(member);
//        log.info("token: {}", token);
		Cookie cookie = new Cookie("token", token);
		cookie.setMaxAge(60 * 30);
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		log.info("로그인 성공...");
		return "redirect:/";
	}

	@Operation(summary = "Jwt 테스트")
	@GetMapping("/test_jwt")
	@ResponseBody
	public String testJwt(HttpServletRequest request) {
//        log.info("jwt 테스트");
		String token = "";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("token")) {
				token = cookie.getValue();
			}
		}
		if (token.isEmpty()) {
//            log.info("토큰이 없어요...");
		}
//        String token = jwtTokenProvider.resolveToken(request);
//        log.info("test_jwt token: {}", token);
		Authentication auth = jwtTokenProvider.getAuthentication(token);
//        log.info("auth: {}", auth);
//        log.info("principal {}, name {}, authorities {}",
//                auth.getPrincipal(), auth.getName(), auth.getAuthorities());
//        log.info("isValid {}", jwtTokenProvider.validateToken(token));
        return jwtTokenProvider.getUserId(token);
    }

    @GetMapping("/logout2") //시큐리티 때문에 logout 못씀 일단 logout2로 해놓음
    public String logout(HttpServletRequest request, HttpServletResponse response) {
    	if(request.getCookies() == null) {
    		return "redirect:/";
    	}
    	
    	// 카카오 로그아웃 로직
    	KakaoController kakao = new KakaoController();
    	
    	Optional<Cookie> cookie_kakao = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("kakao_access_token")).findFirst();
    	kakao.kakaoLogout(cookie_kakao, response);
    	
    	// 네이버 로그아웃 로직
    	NaverController naver = new NaverController();
    	
    	Optional<Cookie> cookie_naver = Arrays.stream(request.getCookies())
    			.filter(c -> c.getName().equals("naver_access_token")).findFirst();
    	naver.naverLogout(cookie_naver, response);
    	// Jwt 토큰 초기화
    	
        Optional<Cookie> cookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("token")).findFirst();

        if (cookie.isPresent()) {
            cookie.get().setMaxAge(0);
            response.addCookie(cookie.get());
        }

        return "redirect:/";
    }
}