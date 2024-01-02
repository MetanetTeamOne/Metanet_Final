package com.metanet.finalproject.member.controller;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    IMemberService memberService;

//    @GetMapping("/home")
//	public String home() {
//		return "member/home";
//	}
    

    @GetMapping("")
    public String getMember(Principal principal, Model model){
        Member member = memberService.getMember(1);
        model.addAttribute("member", member);

        return "member/member_view";
    }

    @GetMapping("/insert")
    public String insertMember(){

        return "member/signup";
    }

    @PostMapping("/insert")
    public String insertMember(@ModelAttribute Member member) {
        memberService.insertMember(member);

        return "redirct:/login";
    }
    
    @GetMapping("/insertok")
    public String insertOkMember(@ModelAttribute Member member) {

        return "member/signup_ok";
    }

    @GetMapping("/update")
    public String updateMember(Model model) {
        Member member = memberService.getMember(1);
        model.addAttribute("member", member);
        return "member/member_update";
    }
    
    /*    @PostMapping("/update") 이메일 처리가 좋은지 id로 처리하는게 좋은지 고민
    public String updateMember(@ModelAttribute MemberUpdateDto updateDto){
        memberService.updateMember(updateDto);
    }*/
    
    @GetMapping("/password/update")
    public String updatePasswordMember(Principal principal, Model model) {
        Member member = memberService.getMember(1);
        model.addAttribute("member", member);
        return "member/member_password";
    }
    
    @PostMapping("/password/update")
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

        return "redirect:/";
    }
    
  //구독 신청 폼
  	@GetMapping("/subscribe/insert")
  	public String insertSubscribe(Model model, String memberEmail) {
//  	if(memberEmail != null && !memberEmail.equals("")) {
		Member member = memberService.selectMember(memberEmail);
		model.addAttribute("member", member);
		return "member/subscribe";
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
  		return "redirect:/member/home";
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
  		return "redirect:/member/home";
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
  		return "redirect:/home";
  	}
    
    
}
