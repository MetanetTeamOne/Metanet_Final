package com.metanet.finalproject.member.controller;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.paging.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-37-210-134.ap-northeast-2.compute.amazonaws.com:8888",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class AdminMemberController {

    @Autowired
    IMemberService memberService;


    @GetMapping("/admin/member")
    public String memberList(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                             @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, Model model) {
        String state = null;
        int memberCount = memberService.getMemberCount(state);
        //log.info("memberCount: {}", memberCount);
        Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
        pagination.setTotalRecordCount(memberCount);

        //log.info("memberCount: {}", memberCount);
        //log.info("pagination: {}", pagination);
        model.addAttribute("pagination", pagination);
        List<Member> memberList = memberService.getPagingMemberList(pagination);
        //log.info("memberList: {}", memberList);
        model.addAttribute("list", memberList);
        return "admin/adminMember";
    }

    @GetMapping("/admin/member/async")
    public String memberAsyncList(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                             @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                  @RequestParam(value = "state", required = false) String state,  Model model) {
        //log.info("회원목록 비동기 요청");
        //log.info("state: {}", state);
        int memberCount = memberService.getMemberCount(state);
        //log.info("memberCount: {}", memberCount);
        Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
        pagination.setTotalRecordCount(memberCount);

        //log.info("memberCount: {}", memberCount);
        //log.info("pagination: {}", pagination);
        model.addAttribute("pagination", pagination);
        List<Member> memberList = memberService.getPagingMemberListByState(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), state);
        //log.info("memberList: {}", memberList);
        model.addAttribute("list", memberList);
        return "admin/adminMember:: memberTable";
    }

    @GetMapping("/admin/member/{state}")
    public String memberStatus(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                               @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, @PathVariable("state") String state, Model model) {
        //log.info("탈퇴 여부 조회");
        int memberCount = memberService.getMemberCount(state);
        //log.info("memberCount: {}", memberCount);
        Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
        pagination.setTotalRecordCount(memberCount);

        //log.info("state: {}", state);
        //log.info("pagination: {}", pagination);
        model.addAttribute("pagination", pagination);
        //log.info("model에 list 담음");
        model.addAttribute("list", memberService.getPagingMemberListByState(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), state));
        return "admin/adminMember:: memberTable";
    }
}
