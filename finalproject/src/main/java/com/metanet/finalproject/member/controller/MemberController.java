package com.metanet.finalproject.member.controller;

import java.sql.Date;

import com.metanet.finalproject.member.model.*;
import com.metanet.finalproject.role.model.Role;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.role.repository.IRoleRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
@Tag(name = "Member", description = "회원 관리 API")
public class MemberController {

	@Autowired
	IMemberService memberService;

	@Autowired
	IAddressService addressService;

	@Autowired
	IRoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	//	Header에서 Token으로 사용자 이메일 획득
	private String getTokenUserEmail(HttpServletRequest request) {
		log.info("이메일로 토큰 받는중...");
		String token = "";

		try {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")){
					token = cookie.getValue();
				}
			}
		} catch(Exception e) {
			e.getMessage();
		}

		return jwtTokenProvider.getUserId(token);
	}

	@Operation(summary = "회원 정보 조회")
	@GetMapping("")
	public String getMember(Model model, HttpServletRequest request) {
		Member emptyMember = new Member();
		try {
			
		    String userEmail = getTokenUserEmail(request);
		    if (userEmail == null || userEmail.isEmpty()) {
		    	model.addAttribute("dto",emptyMember);
		        return "member/login";
		    }
		    
		    Member member = memberService.selectMember(userEmail);

		    if (member == null) {
		    	model.addAttribute("dto",emptyMember);
		        return "member/login";
		    } else {
		        model.addAttribute("member", member);
		        return "member/member_view";
		    }
		} catch(Exception e){
	    	model.addAttribute("dto",emptyMember);
			return "member/login";
		}  
	}

	@Operation(summary = "회원 가입 view")
	@GetMapping("/insert")
	public String insertMember(HttpSession session, Model model){
		MemberInsertDto member = new MemberInsertDto();
		/*String csrfToken = UUID.randomUUID().toString();
		session.setAttribute("csrfToken", csrfToken);
		log.info("/member/insert, GET {}", csrfToken);*/
		model.addAttribute("dto", member);
		return "member/signup";
	}


	@Operation(summary = "회원 가입")
	@PostMapping("/insert")
	public String insertMember(@Valid @ModelAttribute("dto") MemberInsertDto dto, BindingResult result, HttpSession session, Model model) {
		log.info("회원가입 진행중...");
		if(result.hasErrors()){
			log.info("errors: {}", result);
			return "member/signup";
		}
		if(!dto.getMemberPassword().equals(dto.getReMemberPassword())){
			result.rejectValue("memberPassword", null, "비밀번호가 일치하지 않습니다");
			log.info("errors: {}", result);
			return "member/signup";
		}
//		log.info("=========");
		Member member = new Member();
		Address address = new Address();
		log.info("dto: {}", dto.getMemberEmail());
		Member findMember = memberService.selectMember(dto.getMemberEmail());
		log.info("아이디 {}",findMember);

		if (findMember != null) {
			log.info("같은 아이디가 있습니다");
			model.addAttribute("member", dto);
			return "redirect:/member/signup";
		}
		/*String sessionToken = (String) session.getAttribute("csrfToken");
		if(csrfToken==null || !csrfToken.equals(sessionToken)) {
			throw new RuntimeException("CSRF Token Error.");
		}*/


		try {
			//회원 정보 저장
			String encodedPw = passwordEncoder.encode(dto.getMemberPassword());
			member.setMemberName(dto.getMemberName());
			member.setMemberEmail(dto.getMemberEmail());
			member.setMemberPassword(encodedPw);
			member.setMemberPhoneNumber(dto.getMemberPhoneNumber());
			member.setMemberJoinState("1");
			member.setMemberSubscribe("0");
			member.setMemberSubscribeDate(new Date(0));
			member.setMemberCard("0");
			memberService.insertMember(member);

			int memberId = memberService.getMemberId(dto.getMemberEmail());
			log.info("memberId: {}", memberId);

			//권한 부여
			Role role = new Role();
			role.setMemberId(memberId);
			role.setRoleName("ROLE_USER");
			roleRepository.insertRole(role);


			//주소 저장
			address.setAddressZipcode(dto.getAddressZipcode());
			address.setAddressRoad(dto.getAddressRoad());
			address.setAddressContent(dto.getAddressContent());
			address.setAddressCategory("3");
			address.setAddressDetail("null");
			address.setMemberId(memberId);

			log.info("address: {}", address);
			addressService.insertAddress(address);
		} catch (DuplicateKeyException e) {
			member.setMemberEmail(null);
			model.addAttribute("member", member);
			model.addAttribute("message", "id가 이미 있습니다.");
			return "member/signup";
		}
		// 수정 필요
		session.invalidate();
        return "redirect:/member/signup_ok";
    }
    
	@Operation(summary = "회원 가입 완료 view")
	@GetMapping("/signup_ok")
	public String insertOkMember(HttpServletRequest request, Model model) {
//    	Member member = memberService.selectMember(getTokenUserEmail(request)); 회원가입 완료 페이지는 회원정보 안뿌리고 그냥 가입확인만 하기로함
//    	model.addAttribute("member",member);
		return "member/signup_ok";
	}

	@Operation(summary = "회원 정보 수정 view")
	@GetMapping("/update")
	public String updateMember(Model model, HttpServletRequest request) {
		Member member = memberService.selectMember(getTokenUserEmail(request));
		model.addAttribute("updateMember", member);
		return "member/member_update";
	}

	@Operation(summary = "회원 정보 수정")
	@PostMapping("/update")
	public String updateMember(@Valid @ModelAttribute("updateMember") MemberUpdateDto member, BindingResult result, HttpServletRequest request){
		if (result.hasErrors()) {
			log.info("errors: {}", result);
			return "member/member_update";
		}
		memberService.updateMember(member, getTokenUserEmail(request));
		return "redirect:/member";
	}

	@Operation(summary = "회원 비밀번호 수정 view")
	@GetMapping("/password")
	public String updatePasswordMember(HttpServletRequest request, Model model) {
		Member member = memberService.selectMember(getTokenUserEmail(request));
		model.addAttribute("member", member);
		return "member/member_password";
	}

	@Operation(summary = "회원 비밀번호 수정")
	@PostMapping("/password")
	public String updatePasswordMember(HttpServletRequest request, RedirectAttributes model, MemberUpdateDto member) {
		Member dbMember = memberService.selectMember(getTokenUserEmail(request));
		if(member.getMemberPassword() == null && !passwordEncoder.matches(member.getMemberPassword(), dbMember.getMemberPassword())) {
			model.addFlashAttribute("message", "비밀번호가 맞지 않습니다.");
			return "redirect:/member/password";
		}
		member.setMemberPassword(passwordEncoder.encode(member.getNewPassword()));
		memberService.updateMember(member, getTokenUserEmail(request));
		return "redirect:/member";

	}

	@Operation(summary = "회원 삭제 view")
	@GetMapping("/delete")
	public String memberDeleteForm(HttpServletRequest request, Model model){
		String email = getTokenUserEmail(request);
		MemberDeleteDto dto = new MemberDeleteDto();
		dto.setMemberEmail(email);
		model.addAttribute("deleteMember", dto);
		return "member/member_delete";
	}

	@Operation(summary = "회원 삭제")
    @PostMapping("/delete")
    public String deleteMember(HttpServletRequest request, Member member, Model model){
    	try {
			String dbpw = memberService.selectMember(getTokenUserEmail(request)).getMemberPassword();
			if(member.getMemberPassword() != null && passwordEncoder.matches(member.getMemberPassword(), dbpw)) {
				memberService.deleteMember(getTokenUserEmail(request), dbpw);
				return "redirect:/logout2";
			}else {
				model.addAttribute("message", "WRONG_PASSWORD");
				return "redirect:/member/member_delete";
			}
		}catch(Exception e){
			model.addAttribute("message", "fail");
			e.printStackTrace();
			return "redirect:/member/member_delete";
		}
    }

	@Operation(summary = "회원 구독 view")
	@GetMapping("/subscribe")
	public String selectSubscribe(HttpServletRequest request, Model model) {
		Member member = memberService.selectMember(getTokenUserEmail(request));
		model.addAttribute("member",member);
		return "member/subscribe_view";
	}

	@Operation(summary = "회원 구독")
	@PostMapping("/subscribe")
	public String updateSubscribe(HttpServletRequest request, Member member, Model model) {
		member.setMemberEmail(getTokenUserEmail(request));
		memberService.updateSubscribe(member);
		return "redirect:/member/subscribe";
	}


  	@GetMapping("/card")
  	public String getCard(HttpServletRequest request, Model model, String memberEmail) {
		Member member = memberService.selectMember(getTokenUserEmail(request));
		model.addAttribute("member", member);
		return "member/card_view";
//  			}else {
//  				return "member/login";
//  			}
	}


	//카드 등록 폼
	@GetMapping("/card/insert")
	public String insertCard(HttpServletRequest request, Model model) {
//  			if(memberEmail != null && !memberEmail.equals("")) {
		Member member = memberService.selectMember(getTokenUserEmail(request));
		model.addAttribute("member", member);
		return "member/card_insert";
//  			}else {
//  				return "member/login";
//  			}
	}

	//카드 등록 처리
	@PostMapping("/card/insert")
	public String insertCard(Member member, Model model, String memberEmail) {
		memberService.insertCard(memberEmail);
		model.addAttribute("member", member);
		System.out.println("===카드 등록 완료===");
		return "redirect:/member/card";
	}

	//카드 해지 구현 필요
	//카드 등록 처리
	@PostMapping("/card/delete")
	public String deleteCard(Member member, Model model) {
		// 카드 해지 서비스 로직 필요
		model.addAttribute("member", member);
		System.out.println("===카드 해지 완료===");
		return "redirect:/member/card";
	}


}