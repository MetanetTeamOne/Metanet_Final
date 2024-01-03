package com.metanet.finalproject.member.controller;


import java.security.Principal;
import java.sql.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.model.MemberInsertDto;
import com.metanet.finalproject.member.service.IMemberService;

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
	PasswordEncoder passwordEncoder;

    @GetMapping("/home")
	public String home() {
		return "member/home";
	}
    
    @GetMapping("")
    public String getMember(Principal principal, Model model){
        Member member = memberService.getMember(1);
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
		log.info("dto: {}", dto);

		Member findMember = memberService.selectMember(dto.getMemberEmail());
		if (findMember != null) {
			log.info("같은 아이디가 있습니다");
			model.addAttribute("dto", dto);
			return "member/signup";
		}
		/*String sessionToken = (String) session.getAttribute("csrfToken");
		if(csrfToken==null || !csrfToken.equals(sessionToken)) {
			throw new RuntimeException("CSRF Token Error.");
		}*/


		try {
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
		session.invalidate();
        return "redirect:member/signup_ok";
    }
    
  /*  @GetMapping("/insertok")
    public String insertOkMember(@ModelAttribute Member member) {
        return "member/signup_ok";
    }*/

    @GetMapping("/update")
    public String updateMember(Principal principal, Model model) {
        Member member = memberService.getMember(1);
        model.addAttribute("member", member);
        return "member/member_update";
    }
    
    /*@PostMapping("/update")
    public String updateMember(Model model) {
        Member member = memberService.getMember(1);
        model.addAttribute("member", member);
        return "redirect:/member";
    }*/
    
	@PostMapping("/update") //이메일 처리가 좋은지 id로 처리하는게 좋은지 고민
    public String updateMember(@ModelAttribute Member updateDto){
        memberService.updateMember(updateDto, "chlrkdls12269@gmail.com"); // 추후 시큐리티 적용 후 변경 예정

		return "redirect:/member";
    }
    
    @GetMapping("/password")
    public String updatePasswordMember(Principal principal, Model model) {
        Member member = memberService.getMember(1);
        model.addAttribute("member", member);
        return "member/member_password";
    }
    
    @PostMapping("/password")
    public String updatePasswordMember(Model model) {
        Member member = memberService.getMember(1);
        model.addAttribute("member", member);
        return "redirect:/member";
    }

    @GetMapping("/delete")
    public String memberDeleteForm(Model model){
        Member member = memberService.getMember(1);
        model.addAttribute("member", member);

        return "signout";
    }

    @PostMapping("/delete")
    public String deleteMember(@RequestParam String memberPassword){
        memberService.deleteMember(memberPassword);

        return "redirect:/member";
    }
    
  	//구독 상태 조회
  	@GetMapping("/subscribe")
  	public String selectSubscribe(Model model, String memberEmail) {
//  		if(memberEmail != null && !memberEmail.equals("")) {
  		System.out.println("===구독 상태 조회===");
  		memberService.selectSubscribe(memberEmail);
  		return "member/subscribe_view";
//  		}else {
//  			return "member/login";
//  		}
  }
    
  //구독 신청 폼
  	@GetMapping("/subscribe/insert")
  	public String insertSubscribe(Model model, String memberEmail) {
//  	if(memberEmail != null && !memberEmail.equals("")) {
		Member member = memberService.selectMember(memberEmail);
		model.addAttribute("member", member);
		return "member/subscribe_view";
//  	}else {
//  		return "member/login";
//  	}
  	}
  	
  	//구독 신청 처리
  	@PostMapping("/subscribe/insert")
  	public String insertSubscribe(Member member, Model model) {
  		memberService.insertSubscribe(member);
  		model.addAttribute("member", member);
  		System.out.println("===구독신청 완료===");
  		return "redirect:/member/subscribe";
  	}
  	
  	//구독 해지 폼
  	@GetMapping("/subscribe/update")
  	public String updateSubscribe(Model model, String memberEmail) {
//  	if(memberEmail != null && !memberEmail.equals("")) {
  			Member member = memberService.selectMember(memberEmail);
  			model.addAttribute("member", member);
  			return "member/subscribe_update";
//  		}else {
//  			return "member/login";
//  		}
  	}
  	
  	//구독 해지 처리
  	@PostMapping("/subscribe/update")
  	public String updateSubscribe(Member member, Model model) {
  		//member.setMemberSubscribe("0");
  		memberService.updateSubscribe(member);
  		model.addAttribute("member", member);
  		System.out.println("===구독해지 완료===");
  		return "redirect:/member/subscribe_view";
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
