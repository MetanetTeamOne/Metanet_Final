package com.metanet.finalproject.member.controller;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.paging.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class AdminMemberController {

    @Autowired
    IMemberService memberService;


    @GetMapping("/admin/member")
    public String memberList(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                             @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, Model model) {
        int memberCount = memberService.getMemberCount();
        Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
        pagination.setTotalRecordCount(memberCount);

        log.info("memberCount: {}", memberCount);
        log.info("pagination: {}", pagination);
        model.addAttribute("pagination", pagination);
        List<Member> memberList = memberService.getPagingMemberList(pagination);
        log.info("memberList: {}", memberList);
        model.addAttribute("list", memberList);
        return "admin/adminMember";
    }

//    @GetMapping("/admin/member/{state}")
    public String memberStatus(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                               @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, @PathVariable("state") String state, Model model) {
        log.info("탈퇴 여부 조회");
        int memberCount = memberService.getMemberCount();
        Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
        pagination.setTotalRecordCount(memberCount);

        log.info("state: {}", state);
        log.info("pagination: {}", pagination);
        model.addAttribute("pagination", pagination);
        model.addAttribute("list", memberService.getPagingMemberListByState(pagination, state));
        log.info("model에 list 담음");
        if (state.equals("2")) {
            model.addAttribute("list", memberService.getPagingMemberList(pagination));
        }
        return "admin/adminMember:: memberTable";
    }
}
