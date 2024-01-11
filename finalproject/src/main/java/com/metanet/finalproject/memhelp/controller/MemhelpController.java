package com.metanet.finalproject.memhelp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.files.model.Files;
import com.metanet.finalproject.files.service.IFilesService;
import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.laundry.service.ILaundryService;
import com.metanet.finalproject.laundry_category.service.ILaundryCategoryService;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.memhelp.dto.MemhelpInsertDto;
import com.metanet.finalproject.memhelp.model.Memhelp;
import com.metanet.finalproject.memhelp.model.MemhelpSearchByMemberId;
import com.metanet.finalproject.memhelp.service.IMemhelpService;
import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.model.OrdersDetails;
import com.metanet.finalproject.orders.model.OrdersDetailsLaundryPlus;
import com.metanet.finalproject.orders.service.IOrdersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/memhelp")
@Tag(name = "Memhelp", description = "문의사항 관리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-39-151-127.ap-northeast-2.compute.amazonaws.com:8888/",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class MemhelpController {
	
//	@Value("${file.upload.directory}")
//	private String uploadDirectory;
	
	@Autowired
	IOrdersService ordersService;
	
	@Autowired
	IMemberService memberService;

	@Autowired
	IFilesService fileService;

	@Autowired
	IAddressService addressService;

	@Autowired
	ILaundryCategoryService laundryCategoryService;

	@Autowired
	ILaundryService laundryService;
	
	@Autowired
	IMemhelpService memhelpService;

	Files files;
	
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

	@Operation(summary = "회원별 문의사항 조회")
	@GetMapping("")
	public String getMemberhelp(HttpServletRequest request, Model model) {
		// jwt 토큰에서 멤버 정보 불러오기
		int memberId = memberService.getMemberId(getTokenUserEmail(request));
		
		System.out.println("접속회원 ID : " + memberId);
		
		List<MemhelpSearchByMemberId> memhelpSearchByMemberId = memhelpService.searchMemhelp(memberId);
		
		System.out.println("접속한 회원의 문의사항 리스트 : " + memhelpSearchByMemberId);
				
		model.addAttribute("memhelpList", memhelpSearchByMemberId);
		return "member/memhelp_view";
	}
	
	@Operation(summary = "회원별 문의사항 등록 view")
	@GetMapping("/insert")
	public String insertMemberhelp(HttpServletRequest request, Model model, String st) {
		MemhelpInsertDto memhelp = new MemhelpInsertDto();
		
		model.addAttribute("memhelp", memhelp);
		
		return "member/memhelp_insert";
	}
	
	@Operation(summary = "회원별 문의사항 등록")
	@PostMapping("/insert")
	public String insertMemberhelp(@ModelAttribute("memhelp") MemhelpInsertDto memhelp, HttpServletRequest request, Model model) {
		System.out.println("프론트에서 신청버튼 눌러서 보낸 값" + memhelp);
		
		int memberId = memberService.getMemberId(getTokenUserEmail(request));
		
		Memhelp insertMemhelp = new Memhelp();
		
		Date now = new Date();
		
		insertMemhelp.setMemHelpNum(0);
		insertMemhelp.setMemHelpTitle(memhelp.getMemHelpTitle());
		insertMemhelp.setMemHelpContent(memhelp.getMemHelpContent());
		insertMemhelp.setMemHelpFile(memhelp.getMemHelpFile());
		insertMemhelp.setMemHelpState("0");
		insertMemhelp.setMemHelpDate(new java.sql.Date(now.getTime()));
		insertMemhelp.setMemberId(memberId);
		insertMemhelp.setMemCategoryNum(memhelp.getMemCategoryNum());
		
		System.out.println("들어갈 memhelp 내용 : " + insertMemhelp);

		memhelpService.insertMemhelp(insertMemhelp);
		
		return "redirect:/memhelp";
	}
}