package com.metanet.finalproject.member.kakaocontroller;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.model.MemberInsertDto;
import com.metanet.finalproject.member.model.MemberLoginDto;
import com.metanet.finalproject.member.service.IMemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/kakao")
@Tag(name = "Kakao Login", description = "카카오 로그인 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-43-201-12-132.ap-northeast-2.compute.amazonaws.com:8888",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
@Slf4j
public class KakaoController {
	@Autowired
	IMemberService memberService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Value("${kakao.client-id}")
	private String KakaoClientId; 
	
	@Value("${kakao.redirect-uri}")
	private String KakaoRedirectUri;
	
	@Value("${kakao.response-type}")
	private String KakaoResponseType;
	
	@Value("${kakao.grant-type}")
	private String KakaoGrantType;
	
	@Value("${kakao.client-secret}")
	private String KakaoClientSecret;
	
	@Value("${kakao.getaccesstoken-url}")
	private String getAccessTokenURL;
    
	@Value("${kakao.getkakaouserinfo-url}")
	private String getKakaoUserInfoURL;
	
	
	private String KakaoUserAuthorizationCode = null;
	
	private String KakaoJwtToken = null;
	
	public String access_token = "";
	private String refresh_token = "";
	private String token_type = "";
		
	// 조회된 카카오 로그인 유저 정보
	private String user_name = null;
	private String user_email = null;
	private String user_phone_number = null;
    

	@Operation(summary = "카카오 로그인 화면 API")
	@GetMapping("/login")
	public String kakaoLogin() {
		// Kakao 로그인 페이지 URL 생성
	    String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?" +
	            "client_id=" + KakaoClientId +
	            "&redirect_uri=" + KakaoRedirectUri +
	            "&response_type=" + KakaoResponseType;
		
		// Kakao 로그인 페이지로 리디렉션
		return "redirect:" + kakaoUrl;
	}
	
	@Operation(summary = "카카오 액세스 토큰 발급 API")
	@GetMapping("/loginok")
	public String kakaoLoginOk(@RequestParam String code, Model model, HttpServletResponse response, HttpServletRequest request) {
		// 1. 인가 코드 받기 (@RequestParam String code)
		
		// 2. 유저 인증 코드 받기
		KakaoUserAuthorizationCode = code;
				
		System.out.println(KakaoUserAuthorizationCode);
		
		// 3. 유저 인증 코드를 통해 액세스 토큰 발급 받기
		if(KakaoUserAuthorizationCode!=null&&!KakaoUserAuthorizationCode.equals("")) {
			try {
				URL url = new URL(getAccessTokenURL);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				
				//POST 요청을 위해 기본값이 false인 setDoOutput을 true로
	            conn.setRequestMethod("POST");
	            conn.setDoOutput(true);

	            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
	            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
	            StringBuilder sb = new StringBuilder();
	            
	            sb.append("grant_type="+KakaoGrantType);
	            sb.append("&client_id="+KakaoClientId); // TODO REST_API_KEY 입력
	            sb.append("&client_secret="+KakaoClientSecret);
	            sb.append("&redirect_uri="+KakaoRedirectUri); // TODO 인가코드 받은 redirect_uri 입력
	            sb.append("&code=" + KakaoUserAuthorizationCode);
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
	            System.out.println("response body : " + result);

	            JSONParser parser = new JSONParser();
	            try {
	                JSONObject jsonObject = (JSONObject) parser.parse(result);

	                // 필요한 값 추출
	                access_token = (String) jsonObject.get("access_token");
	                token_type = (String) jsonObject.get("token_type");
	                refresh_token = (String) jsonObject.get("refresh_token");
	                long expiresIn = (long) jsonObject.get("expires_in");
	                String scope = (String) jsonObject.get("scope");
	                long refreshTokenExpiresIn = (long) jsonObject.get("refresh_token_expires_in");

	                // 파싱된 값 출력
	                System.out.println("Access Token: " + access_token);
	                System.out.println("Token Type: " + token_type);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }

	            br.close();
	            bw.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// 4. 발급받은 액세스 토큰을 통해서 유저 인증정보 불러오기
		try {
			URL url = new URL(getKakaoUserInfoURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	        conn.setRequestProperty("Authorization", "Bearer " + access_token);
	        conn.setDoOutput(true);
	        
	        int responseCode = conn.getResponseCode();
	        log.info("회원조회 response code : {}", responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("access token response body : " + result);
            
            JSONParser parser = new JSONParser();
            
            JSONObject tokenResponseJsonObject = (JSONObject) parser.parse(result);
            
            System.out.println(tokenResponseJsonObject.get("kakao_account"));
            
            JSONObject kakaoAccount = (JSONObject) tokenResponseJsonObject.get("kakao_account");
            
            user_name = (String) kakaoAccount.get("name");
            user_email = (String) kakaoAccount.get("email");
            String get_user_phonenumber = (String) kakaoAccount.get("phone_number");
            
            user_phone_number = "0" + get_user_phonenumber.substring(4).replace("-", "");
            
            System.out.println("카카오 이메일 이름 : " + user_name);
            System.out.println("카카오 이메일 주소 : " + user_email);
            System.out.println("카카오 이메일 전화번호 : " + user_phone_number);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 회원가입 되어있는지 확인하는 로직.
		// 로직 구현 1. 카카오 로그인 전에 회원가입 안되어있을시 회원가입폼으로 넘어가게함.
		// 로직 구현 2. 카카오 로그인 전에 회원가입 되어있을시 등록된 유저 DB 조회하면서 JWT토큰 발급하여 홈으로 넘어가게 한다.
		Member getKakaoUserInfoFromUserDB = memberService.searchMemberByPhonenumber(user_phone_number);
		try {
			// 로직 구현 1.
			if (getKakaoUserInfoFromUserDB != null&!getKakaoUserInfoFromUserDB.equals("")) {
			    System.out.println(getKakaoUserInfoFromUserDB);
			    
			    String token = jwtTokenProvider.generateToken(getKakaoUserInfoFromUserDB);
//		        log.info("token: {}", token);
			    System.out.println("카카오 jwt 토큰 : " + token);
			    
			    // 로그아웃할때 외부에서 로그아웃에서 한번에 카카오 로그아웃까지 처리해야하는데 이게 안됨.
		        // 그래서 추가적으로 세션으로 카카오 액세스 토큰 넘겨줌

			    // 세션에 access_token 추가
		        Cookie cookie_kakao = new Cookie("kakao_access_token", access_token);
		        cookie_kakao.setMaxAge(60 * 30);
		        cookie_kakao.setHttpOnly(true);
//		        cookie_kakao.setSecure(true);
		        cookie_kakao.setPath("/");
		        response.addCookie(cookie_kakao);
			    
		        Cookie cookie = new Cookie("token", token);
		        cookie.setMaxAge(60 * 30);
		        cookie.setHttpOnly(true);
//		        cookie.setSecure(true);
		        cookie.setPath("/");
		        response.addCookie(cookie);
		        
		        System.out.println("카카오 액세스 jwt 발급후 :" + access_token);
		        
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
	
	public void kakaoLogout(Optional<Cookie> cookie_kakao, HttpServletResponse response){
		String KakaoLogoutURL = "https://kapi.kakao.com/v1/user/logout";
		URL url;
		try {
			url = new URL(KakaoLogoutURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			if (cookie_kakao.isPresent()) { // 쿠키가 존재하는지 확인
		        Cookie kakaoCookie = cookie_kakao.get();
		        if (kakaoCookie.getName().equals("kakao_access_token")) { // 원하는 쿠키인지 확인
		            access_token = kakaoCookie.getValue(); // access_token 값 가져오기
		            
		            cookie_kakao.get().setMaxAge(0);
		            response.addCookie(cookie_kakao.get());
		        }
		    }
		    System.out.println("로그아웃 카카오 쿠키 액세스 토큰 : " + access_token);
			
			conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
	        conn.setRequestProperty("Authorization", "Bearer " + access_token);
	        conn.setDoOutput(true);
	        
	        int responseCode = conn.getResponseCode();
	        log.info("카카오 로그아웃 response code : {}", responseCode);
	        
	        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String line = "";
	        String result = "";

	        while ((line = br.readLine()) != null) {
	            result += line;
	        }
	        
	        access_token = "";
		} catch (Exception e) {
//			e.printStackTrace();
			System.out.println("카카오톡 로그인이 아니라서 카카오톡 로그인 로직 처리 안함");
		}
	}
}
