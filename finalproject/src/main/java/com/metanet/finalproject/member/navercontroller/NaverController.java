package com.metanet.finalproject.member.navercontroller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.model.MemberInsertDto;
import com.metanet.finalproject.member.service.IMemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/naver")
@Tag(name = "Naver Login", description = "네이버 로그인 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-37-210-134.ap-northeast-2.compute.amazonaws.com:8888",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
@Slf4j
public class NaverController {
	@Autowired
	IMemberService memberService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	private String naverRedirectId = "http://ec2-3-37-210-134.ap-northeast-2.compute.amazonaws.com:8888/naver/loginok";
	private String naverClientId = "CKbzHQEyk93DOF3jRkqO";
	private String naverClientSecret = "3g3mWH19q6";
	private String naverResponseType = "code";
	
	private String naverCallbackCode = "";
	
	private String naverAccessToken = "";
	private String naverRefreshToken = "";
	private String naverTokenType = "";
	
	private String getNaverAccessTokenURL = "https://nid.naver.com/oauth2.0/token?";
	
	private String naverGrantType = "authorization_code";
	
	private String getNaverGetUserInfoURL = "https://openapi.naver.com/v1/nid/me";
	
	private String naver_user_email = "";
	private String naver_user_phonenumber = "";
	
	private String naver_logout_URL = "https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=CLIENT_ID&client_secret=CLIENT_SECRET&access_token=ACCESS_TOKEN";

	
	// https://nid.naver.com/oauth2.0/authorize?redirect_uri=http://localhost:8085/naver/loginok&client_id=CKbzHQEyk93DOF3jRkqO&response_type=code
	
	@Operation(summary = "네이버 로그인 API")
	@GetMapping("/login")
	public String naverLogin() {
		// Naver 로그인 페이지 URL 생성
	    String naverUrl = "https://nid.naver.com/oauth2.0/authorize?" +
	            "redirect_uri=" + naverRedirectId +
	            "&client_id=" + naverClientId +
	            "&response_type=" + naverResponseType;
		
		// Naver 로그인 페이지로 리디렉션
		return "redirect:" + naverUrl;
	}
	
	@Operation(summary = "네이버 로그인 callback API")
	@GetMapping("/loginok")
	public String naverLoginOk(@RequestParam String code, HttpServletResponse response, Model model) {
		naverCallbackCode = code;
		
		System.out.println("네이버 callback 반환 code : " + code);
		
		// https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=jyvqXeaVOVmV&client_secret=527300A0_COq1_XV33cf&code=EIc5bFrl4RibFls1&state=9kgsGTfH4j7IyAkg  

		if(naverCallbackCode!=null&&!naverCallbackCode.equals("")) {
			try {
				URL url = new URL(getNaverAccessTokenURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					
		        conn.setRequestMethod("POST");
		        conn.setDoOutput(true);

		        //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
		        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		        StringBuilder sb = new StringBuilder();
		            
		        sb.append("grant_type="+naverGrantType);
		        sb.append("&client_id="+naverClientId); // TODO REST_API_KEY 입력
		        sb.append("&client_secret="+naverClientSecret);
		        sb.append("&code=" + naverCallbackCode);
		        bw.write(sb.toString());
		        bw.flush();

		        //결과 코드가 200이라면 성공
		        int responseCode = conn.getResponseCode();
		        System.out.println("responseCode : " + responseCode);
		        //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
		        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		        String line = "";
		        String result = "";

		        while ((line = br.readLine()) != null) {
		           result += line;
		        }
		        
		        System.out.println("네이버 로그인 get Access Token response body : " + result);

		        JSONParser parser = new JSONParser();
		        
		        try {
		           JSONObject jsonObject = (JSONObject) parser.parse(result);
		           
		           naverAccessToken = (String) jsonObject.get("access_token");
		           naverRefreshToken = (String) jsonObject.get("refresh_token");
		           naverTokenType = (String) jsonObject.get("token_type");
			       // 파싱된 값 출력
			       System.out.println("Access Token: " + naverAccessToken);
			       System.out.println("Token Type: " + naverTokenType);
			    } catch (Exception e) {
			    	e.printStackTrace();
			    }
			    br.close();
			    bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			//https://openapi.naver.com/v1/nid/me
		}
		
		if(naverAccessToken!=null&!naverAccessToken.equals("")) {
			try {
				URL url = new URL(getNaverGetUserInfoURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
		        conn.setRequestMethod("POST");
		        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		        conn.setRequestProperty("Authorization", "Bearer " + naverAccessToken);
		        conn.setDoOutput(true);
		        
		        int responseCode = conn.getResponseCode();
		        log.info("네이버 회원조회 response code : {}", responseCode);
		        
		        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            String line = "";
	            String result = "";

	            while ((line = br.readLine()) != null) {
	                result += line;
	            }
	            System.out.println("네이버 회원 조회 결과 : " + result);
	            
	            JSONParser parser = new JSONParser();
	            
	            JSONObject tokenResponseJsonObject = (JSONObject) parser.parse(result);
	            
	            System.out.println(tokenResponseJsonObject.get("response"));
//	            
	            JSONObject naverAccount = (JSONObject) tokenResponseJsonObject.get("response");
//	            

	            naver_user_email = (String) naverAccount.get("email");
	            String temp_phonenumber = (String) naverAccount.get("mobile");
	            
	            naver_user_phonenumber = temp_phonenumber.replace("-", "");
	            
	            System.out.println("네이버 유저 이메일 : " + naver_user_email + " 네이버 유저 핸드폰 번호 : " + naver_user_phonenumber);

	            
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		Member getNaverUserInfoFromUserDB = memberService.searchMemberByPhonenumber(naver_user_phonenumber);
		try {
			// 로직 구현 1.
			if (getNaverUserInfoFromUserDB != null&!getNaverUserInfoFromUserDB.equals("")) {
			    System.out.println(getNaverUserInfoFromUserDB);
			    
			    String token = jwtTokenProvider.generateToken(getNaverUserInfoFromUserDB);
//		        log.info("token: {}", token);
			    System.out.println("네이버 jwt 토큰 : " + token);
			    
			    // 로그아웃할때 외부에서 로그아웃에서 한번에 카카오 로그아웃까지 처리해야하는데 이게 안됨.
		        // 그래서 추가적으로 쿠키에 네이버 액세스 토큰 넘겨줌

			    // 쿠키에 access_token 추가

			    
		        Cookie cookie_naver = new Cookie("naver_access_token", naverAccessToken);
		        cookie_naver.setMaxAge(60 * 30);
		        cookie_naver.setHttpOnly(true);
//		        cookie_naver.setSecure(true);
		        cookie_naver.setPath("/");
		        response.addCookie(cookie_naver);
			    
		        Cookie cookie = new Cookie("token", token);
		        cookie.setMaxAge(60 * 30);
		        cookie.setHttpOnly(true);
//		        cookie.setSecure(true);
		        cookie.setPath("/");
		        response.addCookie(cookie);
		        
		        System.out.println("네이버 액세스 jwt 발급후 :" + naverAccessToken);
		        
			    return "redirect:/";
			} 
			// 로직 구현 2.
			else {
			    System.out.println("전화번호에 일치하는 유저 정보 없음");
			    MemberInsertDto member = new MemberInsertDto();
		        model.addAttribute("dto", member);
		        model.addAttribute("showAlert", true);
			    return "member/signup";
			}
		}catch(Exception e){
			MemberInsertDto member = new MemberInsertDto();
	        model.addAttribute("dto", member);
	        model.addAttribute("showAlert", true);
			return "member/signup";
		}
	}
	
	// 네이버 로그아웃 로직
	// 아직 구현 안함
	public void naverLogout(Optional<Cookie> cookie_naver, HttpServletResponse response) {
//		"https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=CLIENT_ID&client_secret=CLIENT_SECRET&access_token=ACCESS_TOKEN";
		String NaverLogoutURL = "https://nid.naver.com/oauth2.0/token";
		
		URL url;
		try {
			url = new URL(NaverLogoutURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			if (cookie_naver.isPresent()) { // 쿠키가 존재하는지 확인
		        Cookie naverCookie = cookie_naver.get();
		        if (naverCookie.getName().equals("naver_access_token")) { // 원하는 쿠키인지 확인
		        	naverAccessToken = naverCookie.getValue(); // access_token 값 가져오기

		            cookie_naver.get().setMaxAge(0);
		            response.addCookie(cookie_naver.get());
		        }
		    }
			
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);

	        //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	        StringBuilder sb = new StringBuilder();
	            
	        sb.append("grant_type=delete");
	        sb.append("&client_id="+naverClientId); // TODO REST_API_KEY 입력
	        sb.append("&client_secret="+naverClientSecret);
	        sb.append("&access_token=" +naverAccessToken);
	        bw.write(sb.toString());
	        bw.flush();

	        //결과 코드가 200이라면 성공
	        int responseCode = conn.getResponseCode();
	        System.out.println("responseCode : " + responseCode);
	        //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        String result = "";

	        while ((line = br.readLine()) != null) {
	           result += line;
	        }
	        
	        // naver 정보 초기화
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("네이버 로그인이 아니라서 네이버 로그인 로직 처리 안함");
		}
		
	}
}

