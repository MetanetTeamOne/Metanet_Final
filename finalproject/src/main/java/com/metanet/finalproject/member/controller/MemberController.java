package com.metanet.finalproject.member.controller;

import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.model.MemberInsertDto;
import com.metanet.finalproject.member.model.MemberUpdateDto;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.role.repository.IRoleRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
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
	
    @GetMapping("")
    public String getMember(Model model,HttpServletRequest request){
        Member member = memberService.selectMember(getTokenUserEmail(request));
        if (member == null) {
        	return "member/login";
        }
        model.addAttribute("member", member);
        return "member/member_view";
    }

    @GetMapping("/insert")
    public String insertMember(HttpSession session){
		String csrfToken = UUID.randomUUID().toString();
		session.setAttribute("csrfToken", csrfToken);
		log.info("/member/insert, GET {}", csrfToken);
		return "member/signup";
    }


    @PostMapping("/insert")
    public String insertMember(@ModelAttribute MemberInsertDto dto, HttpSession session, Model model) {
		log.info("회원가입 진행중...");
		Member member = new Member();
		Address address = new Address();
		log.info("dto: {}", dto.getMemberEmail());
		Member findMember = memberService.selectMember(dto.getMemberEmail());
		log.info("아이디 {}",findMember);
		if (findMember != null) {
			log.info("같은 아이디가 있습니다");
			model.addAttribute("dto", dto);
			return "redirect:/member/signup";
		}
		/*String sessionToken = (String) session.getAttribute("csrfToken");
		if(csrfToken==null || !csrfToken.equals(sessionToken)) {
			throw new RuntimeException("CSRF Token Error.");
		}*/


//		try {
//			//회원 정보 저장
//			String encodedPw = passwordEncoder.encode(dto.getMemberPassword());
//			member.setMemberName(dto.getMemberName());
//			member.setMemberEmail(dto.getMemberEmail());
//			member.setMemberPassword(encodedPw);
//			member.setMemberPhoneNumber(dto.getMemberPhoneNumber());
//			member.setMemberJoinState("1");
//			member.setMemberSubscribe("0");
//			member.setMemberSubscribeDate(new Date(0));
//			member.setMemberCard("0");
//			memberService.insertMember(member);
//
//			int memberId = memberService.getMemberId(dto.getMemberEmail());
//			log.info("memberId: {}", memberId);
//
//			//권한 부여
//			Role role = new Role();
//			role.setMemberId(memberId);
//			role.setRoleName("ROLE_USER");
//			roleRepository.insertRole(role);
//
//
//			//주소 저장
//			address.setAddressZipcode(dto.getAddressZipcode());
//			address.setAddressRoad(dto.getAddressRoad());
//			address.setAddressContent(dto.getAddressContent());
//			address.setAddressCategory("3");
//			address.setAddressDetail("null");
//			address.setMemberId(memberId);
//
//			log.info("address: {}", address);
//			addressService.insertAddress(address);
//		} catch (DuplicateKeyException e) {
//			member.setMemberEmail(null);
//			model.addAttribute("member", member);
//			model.addAttribute("message", "id가 이미 있습니다.");
//			return "member/signup";
//		}
//		// 수정 필요		
//		session.invalidate();
        return "redirect:/member/signup_ok";
    }
    
    @GetMapping("/signup_ok")
    public String insertOkMember(HttpServletRequest request, Model model) {
    	Member member = memberService.selectMember(getTokenUserEmail(request));
    	model.addAttribute("member",member);
        return "member/signup_ok";
    }

    @GetMapping("/update")
    public String updateMember(Model model, HttpServletRequest request) {
        Member member = memberService.selectMember(getTokenUserEmail(request));
        model.addAttribute("member", member);
        return "member/member_update";
    }
    
	@PostMapping("/update")
    public String updateMember(@ModelAttribute MemberUpdateDto member, HttpServletRequest request){
        memberService.updateMember(member, getTokenUserEmail(request));
		return "redirect:/member";
    }
    
    @GetMapping("/password")
    public String updatePasswordMember(HttpServletRequest request, Model model) {
        Member member = memberService.selectMember(getTokenUserEmail(request));
        model.addAttribute("member", member);
        return "member/member_password";
    }
    
    @PostMapping("/password")
    public String updatePasswordMember(HttpServletRequest request, Model model, MemberUpdateDto member) {
    	Member dbMember = memberService.selectMember(getTokenUserEmail(request));
    	if (!member.getMemberPassword().equals(dbMember.getMemberPassword())) {
    		model.addAttribute("message","비밀번호가 맞지 않습니다.");
            return "redirect:/member/password";
    	}
    	
    	memberService.updateMember(member, member.getNewPassword());    	
    	return "redirect:/member";
    	
    }

    @GetMapping("/delete")
    public String memberDeleteForm(HttpServletRequest request, Model model){
        Member member = memberService.selectMember(getTokenUserEmail(request));
        model.addAttribute("member", member);
        return "member/member_delete";
    }

    @PostMapping("/delete")
    public String deleteMember(HttpServletRequest request, Member member, Model model){
    	Member dbMember = memberService.selectMember(getTokenUserEmail(request));
    	if (!dbMember.getMemberPassword().equals(member.getMemberPassword())) {
    		model.addAttribute("message","비밀번호가 맞지 않습니다.");
            return "redirect:/member/delete";
    	}
    	
        memberService.deleteMember(getTokenUserEmail(request), member.getMemberPassword());
        return "redirect:/logout";
    }
    
  	@GetMapping("/subscribe")
  	public String selectSubscribe(HttpServletRequest request, Model model) {
  		Member member = memberService.selectMember(getTokenUserEmail(request));
  		model.addAttribute("member",member);
  		return "member/subscribe_view";
  	}
  	
  	@PostMapping("/subscribe")
  	public String updateSubscribe(HttpServletRequest request, Member member, Model model) {
  		member.setMemberEmail(getTokenUserEmail(request));
  		memberService.updateSubscribe(member);
  		return "redirect:/member/subscribe";
  	}
  	

  	@GetMapping("/card")
  	public String getCard(Model model, String memberEmail) {
//  			if(memberEmail != null && !memberEmail.equals("")) {
  			Member member = memberService.selectMember(memberEmail);
  			model.addAttribute("member", member);
  			return "member/card_view";
//  			}else {
//  				return "member/login";
//  			}
  	}

  	
  	//카드 등록 폼
  	@GetMapping("/card/insert")
  	public String insertCard(Model model, String memberEmail) {
//  			if(memberEmail != null && !memberEmail.equals("")) {
  			Member member = memberService.selectMember(memberEmail);
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
