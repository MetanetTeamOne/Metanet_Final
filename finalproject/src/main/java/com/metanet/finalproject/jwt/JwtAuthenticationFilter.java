package com.metanet.finalproject.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	
        // 헤더에서 JWT 를 받아옵니다.
//        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        
//        System.out.println("여기!!!!!!_+_ㅒ_$%@!_%@_@!");
        
        String Jwt_value = null;
        
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            // 쿠키 가져오기
            Cookie[] cookies = httpRequest.getCookies();

            if (cookies != null){
                for (Cookie cookie : cookies) {
//            	System.out.println("쿠키 이름 Key : " + cookie.getName());
//            	System.out.println("쿠키 데이터 : " + cookie.getValue());

                    Jwt_value = cookie.getValue();
                }
            }
            else{
//                System.out.println("token 없음");
            }

        }
        
//        System.out.println("쿠키 데이터 : " + Jwt_value);
        
//        System.out.println("결과 " + jwtTokenProvider.validateToken(Jwt_value));
        
        
        // 유효한 토큰인지 확인합니다.
        if (Jwt_value != null && jwtTokenProvider.validateToken(Jwt_value)) {
//        	System.out.println(" 토큰 정보 " + Jwt_value);
        	
        	
            // 토큰이 유효하면 토큰으로부터 유저 정보를 받아옵니다.
            Authentication authentication = jwtTokenProvider.getAuthentication(Jwt_value);
            // SecurityContext 에 Authentication 객체를 저장합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}