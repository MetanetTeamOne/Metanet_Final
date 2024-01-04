package com.metanet.finalproject;


import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class LoginController {
    @Autowired
    IMemberService memberService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 로그인
    @GetMapping("/login")
    public String loginPage() {
        System.out.println("login");
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("userid") String email, @RequestParam("userpw") String password, HttpServletResponse response){
        log.info("로그인 진행중...");
        log.info("email: {} password: {}", email, password);
        Member member = memberService.selectMember(email);
        log.info("member: {}", member);
        if(member==null) {
            throw new IllegalArgumentException("사용자가 없습니다.");
        }
        if (!passwordEncoder.matches(password, member.getMemberPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        String token = jwtTokenProvider.generateToken(member);
        log.info("token: {}", token);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60*60*24*7);
        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        log.info("로그인 성공...");
        return "redirect:/";
    }

    @GetMapping("/test_jwt")
    @ResponseBody
    public String testJwt(HttpServletRequest request) {
        String header = request.getHeader("X-AUTH-TOKEN");
        log.info("header: {}", header);
        String token = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")){
                token = cookie.getValue();
            }
        }
//        String token = jwtTokenProvider.resolveToken(request);
        log.info("test_jwt token: {}", token);
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        log.info("auth: {}", auth);
        log.info("principal {}, name {}, authorities {}",
                auth.getPrincipal(), auth.getName(), auth.getAuthorities());
        log.info("isValid {}", jwtTokenProvider.validateToken(token));
        return jwtTokenProvider.getUserId(token);
    }
}
