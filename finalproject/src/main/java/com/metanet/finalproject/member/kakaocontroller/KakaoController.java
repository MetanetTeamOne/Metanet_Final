package com.metanet.finalproject.member.kakaocontroller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metanet.finalproject.member.service.IMemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member/kakao")
@Tag(name = "Kakao Login", description = "카카오 로그인 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-39-151-127.ap-northeast-2.compute.amazonaws.com:8888/",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*")
@Slf4j
public class KakaoController {
	@Autowired
	IMemberService memberService;
	
	String KakaoClientId = "2e5a8c7c7c5bae987fd68ea4def1c608"; 
	String KakaoRedirectUri = "http://localhost:8085/member/kakao/loginok";
    String KakaoResponseType = "code";
	
	String KakaoGrantType = "authorization_code";
	
	String KakaoClientSecret = "oHH6Rh6LEfzyTcGOlBaZA0tvW0pGymYB";
	
	
	String KakaoCode = null;
	
	String KakaoJwtToken = null;
	
	String access_token = "";
    String refresh_token = "";
    String token_type = "";
    String getAccessTokenURL = "https://kauth.kakao.com/oauth/token";
    
    String getKakaoUserInfoURL = "https://kapi.kakao.com/v2/user/me";

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
	public String ok(@RequestParam String code) {
		// 1. 인가 코드 받기 (@RequestParam String code)
		
		// 2. 유저 인증 코드 받기
		String KakaoUserAuthorizationCode = code;
				
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
	            
	            sb.append("grant_type=authorization_code");
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
	                String accessToken = (String) jsonObject.get("access_token");
	                String tokenType = (String) jsonObject.get("token_type");
	                String refreshToken = (String) jsonObject.get("refresh_token");
	                long expiresIn = (long) jsonObject.get("expires_in");
	                String scope = (String) jsonObject.get("scope");
	                long refreshTokenExpiresIn = (long) jsonObject.get("refresh_token_expires_in");

	                // 파싱된 값 출력
	                System.out.println("Access Token: " + accessToken);
	                System.out.println("Token Type: " + tokenType);
	                // 나머지 필드에 대한 작업 수행
	                
	                access_token = accessToken;
	                refresh_token = refreshToken;
	                token_type = tokenType;
	                
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
            // JSON 문자열을 JSONObject로 변환
            JSONObject tokenResponseJsonObject = (JSONObject) parser.parse(result);

            // 필요한 정보 추출
//            long id = jsonObject.getLong("id");
            
            System.out.println(tokenResponseJsonObject.get("kakao_account"));
            
            JSONObject kakaoAccount = (JSONObject) tokenResponseJsonObject.get("kakao_account");
            
            String user_name = (String) kakaoAccount.get("name");
            String user_email = (String) kakaoAccount.get("email");
            String user_phone_number = (String) kakaoAccount.get("phone_number");
            
            System.out.println("카카오 이메일 이름 : " + user_name);
            System.out.println("카카오 이메일 주소 : " + user_email);
            System.out.println("카카오 이메일 전화번호 : " + user_phone_number);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "member/home";
	}
	// http://localhost:8085/oauth/kakao
	// https://kauth.kakao.com/oauth/authorize?client_id=19eeeefd0e612dd5e92798ad5a0566ed&redirect_uri=http://localhost:8085/&response_type=code
}
