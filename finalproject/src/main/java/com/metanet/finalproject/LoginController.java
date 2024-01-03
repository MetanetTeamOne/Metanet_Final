package com.metanet.finalproject;


import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import jakarta.servlet.http.HttpServletRequest;
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
    @ResponseBody
    public String login(@RequestParam("userid") String email, @RequestParam("userpw") String password){
        log.info("로그인 진행중...");
        log.info("email: {} password: {}", email, password);
        Member member = memberService.selectMember(email);
        if(member==null) {
            throw new IllegalArgumentException("사용자가 없습니다.");
        }
        if (!passwordEncoder.matches(password, member.getMemberPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.generateToken(member);
    }

    @GetMapping("/test_jwt")
    @ResponseBody
    public String testJwt(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        log.info("token {}", token);
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        log.info("principal {}, name {}, authorities {}",
                auth.getPrincipal(), auth.getName(), auth.getAuthorities());
        log.info("isValid {}", jwtTokenProvider.validateToken(token));
        return jwtTokenProvider.getUserId(token);
    }
}
