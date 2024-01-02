package com.metanet.finalproject.member.controller;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    IMemberService memberService;

    @GetMapping("/{memberId}")
    public String getMember(@PathVariable("memberId") int memberId, Model model){
        Member member = memberService.getMember(memberId);
        model.addAttribute("member", member);

        return "member_view";
    }

    @GetMapping("/insert")
    public String memberInsertForm(){

        return "signup";
    }

    @PostMapping("/insert")
    public String insertMember(@ModelAttribute Member member) {
        memberService.insertMember(member);

        return "redirct:/login";
    }

    @GetMapping("/update/{memberId}")
    public String memberUpdateForm(@PathVariable("memberId") int memberId, Model model) {
        Member member = memberService.getMember(memberId);
        model.addAttribute("member", member);
        return "member_update";
    }

/*    @PostMapping("/update") 이메일 처리가 좋은지 id로 처리하는게 좋은지 고민
    public String updateMember(@ModelAttribute MemberUpdateDto updateDto){
        memberService.updateMember(updateDto);
    }*/

    @GetMapping("/delete/{memberId}")
    public String memberDeleteForm(@PathVariable("memberId") int memberId, Model model){
        Member member = memberService.getMember(memberId);
        model.addAttribute("member", member);

        return "signout";
    }

    @PostMapping("/delete")
    public String deleteMember(@RequestParam String memberPassword){
        memberService.deleteMember(memberPassword);

        return "redirect:/";
    }
}
