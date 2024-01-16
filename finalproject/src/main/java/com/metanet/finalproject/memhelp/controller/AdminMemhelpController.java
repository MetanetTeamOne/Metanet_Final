package com.metanet.finalproject.memhelp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.laundry.service.ILaundryService;
import com.metanet.finalproject.laundry_category.service.ILaundryCategoryService;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.memhelp.service.IMemhelpService;
import com.metanet.finalproject.orders.service.IOrdersService;
import com.metanet.finalproject.paging.Pagination;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Tag(name = "AdminMemhelp", description = "관리자 - 문의사항 관리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-37-210-134.ap-northeast-2.compute.amazonaws.com:8888",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class AdminMemhelpController {
	@Autowired
	IOrdersService ordersService;
	
	@Autowired
	IMemberService memberService;

	@Autowired
	IAddressService addressService;

	@Autowired
	ILaundryCategoryService laundryCategoryService;

	@Autowired
	ILaundryService laundryService;
	
	@Autowired
	IMemhelpService memhelpService;
	
	@Operation(summary = "관리자 주문 관리 API")
	@GetMapping("/admin/memhelp")
	public String searchTotalMemhelp(HttpServletRequest request, Model model) {
		// 문의 현황 구현 로직
		
		// 문의 전체 카운트
		model.addAttribute("maxcount", memhelpService.getAdminMemHelpCount());
		
		// 문의 처리 완료 카운트
		model.addAttribute("complete_cnt", memhelpService.getAdminMemHelpCompleteCount());		
		
		// 문의 처리 미완료 카운트
		model.addAttribute("incomplete_cnt", memhelpService.getAdminMemHelpIncompleteCount());
		
		System.out.println("모든 회원 문의사항 조회 : " + memhelpService.searchAllMemhelp());
		int memHelpCount = memhelpService.getAdminMemHelpCount();
		log.info("memHelpCount: {}", memHelpCount);
		Pagination pagination = new Pagination(1, 10, 10);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(memHelpCount);
		model.addAttribute("pagination", pagination);
//		model.addAttribute("memhelpList", memhelpService.searchAllMemhelp());
		model.addAttribute("memhelpList", memhelpService.searchPagingAllMemhelp(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex()));
		return "admin/adminMemhelp";
	}

	@Operation(summary = "관리자 주문 관리 API")
	@GetMapping("/admin/memhelp/async")
	public String searchTotalMemhelp(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
									 @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
									 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
									 HttpServletRequest request, Model model) {
		log.info("비동기 관리자 문의관리");
		int memHelpCount = memhelpService.getAdminMemHelpCount();
		log.info("memHelpCount: {}", memHelpCount);
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(memHelpCount);
		model.addAttribute("pagination", pagination);
//		model.addAttribute("memhelpList", memhelpService.searchAllMemhelp());
		model.addAttribute("memhelpList", memhelpService.searchPagingAllMemhelp(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex()));
		return "admin/adminMemhelp:: memberTable";
	}
}
