package com.metanet.finalproject.member.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.metanet.finalproject.member.model.MemberDto;
import com.metanet.finalproject.member.service.IMemberService;

import jakarta.servlet.http.HttpSession;

@RestController
public class MemberContorller {
	@Autowired
	IMemberService studentService;	
//	@Autowired
//	IRoleRepository roleRepository;
	

	private static Logger logger = LoggerFactory.getLogger(MemberContorller.class.getName());
	
	@GetMapping("/loginSuccess") //로그인 성공 메시지 출력
	public String loginOk() {
		return "로그인 되었습니다!";
	}
	
	@GetMapping("/logoutSuccess") //로그인 성공 메시지 출력
	public String logoutOk() {
		return "로그아웃 되었습니다!";
	}
	
	@PostMapping("/logout")
	public String logout(HttpSession session, Principal principal) {
		String session_isCheck_userid = principal.getName();
		
		System.out.println(session_isCheck_userid);
		
		if(session_isCheck_userid != null && !session_isCheck_userid.equals("")) {
			session.invalidate();
			return "로그아웃 완료";
		} else {
			return "로그인이 필요한 서비스입니다";
		}
	}
	
	@GetMapping("/mypage") //회원정보 조회
	public MemberDto findStudentById(@RequestBody Map<String, String> map) {
		String Id = map.get("studentId");
		MemberDto member = studentService.findStudentById(Id);
		logger.info("memberId={}", map.get("memberId"));
		logger.info("member={}", member);
		return member;
	}
	

	@PostMapping("/signin") //회원가입
	public String insertStudent(@RequestBody MemberDto member) {
	    PasswordEncoder pwEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	   
	    String unbcrypt_pwd = member.getPassword();
	    String bcrypt_pwd = pwEncoder.encode(unbcrypt_pwd);
	      
	    member.setPassword(bcrypt_pwd);

//	    Role role = new Role();
//	    role.setStudentId(member.getId());
//	    role.setRoleName("ROLE_USER");
	    studentService.insertStudent(member);
//	    roleRepository.insertRole(role);
	    
	    return "ok";
	}

//	@PatchMapping("/mypage/update")
//	public String updateStudent(@RequestBody StudentUpdateDto student, HttpSession session){
//		try {
//			PasswordEncoder pwEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//			String encodedPw = pwEncoder.encode(student.getPassword());
//			logger.info("encodedPw {}", encodedPw);
//			student.setPassword(encodedPw);
//			studentService.updateStudent(student);
//			session.setAttribute("email", student.getEmail());
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "회원정보 수정 실패";
//		}
//		return "회원정보수정 완료";
//	}
	
	@PatchMapping("/mypage/update")
	public String updateStudent(@RequestBody MemberDto member, Principal principal){
		String session_isCheck_userid = principal.getName();
		
		System.out.println(session_isCheck_userid);
		
		if(session_isCheck_userid != null && !session_isCheck_userid.equals("")) {
			try {
				PasswordEncoder pwEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
				   
			    String unbcrypt_pwd = member.getPassword();
			    String bcrypt_pwd = pwEncoder.encode(unbcrypt_pwd);
			      
			    member.setPassword(bcrypt_pwd);
				
			    if (member.getId().equals(session_isCheck_userid)) {
			    	member.setId(session_isCheck_userid);
				    
					studentService.updateStudent(member);
			    }else {
			    	return "회원정보 수정 실패";
			    }
			} catch (Exception e) {
				e.printStackTrace();
				return "회원정보 수정 실패";
			}
				return "회원정보수정 완료";
		}else {
			return "로그인이 필요한 서비스입니다.";
		}
	}
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	@DeleteMapping("/mypage/delete") //회원 정보 삭제
	public String deleteStudent(@RequestBody Map<String, String> map, Principal principal) {
		String session_isCheck_userid = principal.getName();		
		if(session_isCheck_userid != null && !session_isCheck_userid.equals("")) {
			try {
				MemberDto member = studentService.findStudentById(session_isCheck_userid);
				System.out.println();
				String email = map.get("email");
				System.out.println("session email : "+ email + " db email : " + member.getEmail());
				if(email.equals(member.getEmail())){
					System.out.println("삭제!!!!!!!!!!!!!!");
					
					studentService.deleteStudent(email);
					
					return "삭제 성공";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return "삭제 실패";
			}
		} else {
			return "로그인이 필요한 서비스입니다.";
		}
		return "삭제 실패";
		
	}
}
