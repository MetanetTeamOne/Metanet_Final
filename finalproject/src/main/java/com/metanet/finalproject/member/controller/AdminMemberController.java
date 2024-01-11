package com.metanet.finalproject.member.controller;

import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@Slf4j
public class AdminMemberController {

    @Autowired
    IMemberService memberService;

    @Autowired
    IAddressService addressService;


    @GetMapping("/admin/member")
    public String memberList(Model model) {
        List<Member> memberList = memberService.getMemberList();
        model.addAttribute("list", memberList);
        return "admin/adminMember";
    }

    @GetMapping("/admin/member/{state}")
    public String memberStatus(@PathVariable("state") String state, Model model) {
//        log.info("탈퇴 여부 조회");
        model.addAttribute("list", memberService.getMemberListByState(state));
        if (state.equals("2")) {
            model.addAttribute("list", memberService.getMemberList());
        }
        return "admin/adminMember:: memberTable";
    }
}
