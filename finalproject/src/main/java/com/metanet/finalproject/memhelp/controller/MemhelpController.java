package com.metanet.finalproject.memhelp.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.laundry.service.ILaundryService;
import com.metanet.finalproject.laundry_category.service.ILaundryCategoryService;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.memhelp.dto.MemhelpInsertDto;
import com.metanet.finalproject.memhelp.model.Memhelp;
import com.metanet.finalproject.memhelp.model.MemhelpSearchByMemberId;
import com.metanet.finalproject.memhelp.service.IMemhelpService;
import com.metanet.finalproject.orders.service.IOrdersService;
import com.metanet.finalproject.paging.Pagination;
import com.metanet.finalproject.reply.model.Reply;
import com.metanet.finalproject.reply.service.IReplyService;

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
		"http://ec2-3-37-210-134.ap-northeast-2.compute.amazonaws.com:8888",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class MemhelpController {
	
//	@Value("${file.upload.directory}")
//	private String uploadDirectory;
	
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
	
	@Autowired
	IReplyService replyService;

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
		int memHelpCount = memhelpService.getMemHelpCount(memberId);
		log.info("memHelpCount: {}", memHelpCount);
		Pagination pagination = new Pagination(1, 10, 10);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(memHelpCount);
		model.addAttribute("pagination", pagination);


		System.out.println("접속회원 ID : " + memberId);
		
//		List<MemhelpSearchByMemberId> memhelpSearchByMemberId = memhelpService.searchMemhelp(memberId);
		List<MemhelpSearchByMemberId> memhelpSearchByMemberId = memhelpService.searchPagingMemhelp(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), memberId);

		System.out.println("접속한 회원의 문의사항 리스트 : " + memhelpSearchByMemberId);
				
		model.addAttribute("memhelpList", memhelpSearchByMemberId);
		return "member/memhelp_view";
	}

	@Operation(summary = "회원별 문의사항 비동기 조회")
	@GetMapping("/async")
	public String getMemberhelpAsync(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
								@RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
								@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
								HttpServletRequest request, Model model) {
		// jwt 토큰에서 멤버 정보 불러오기
		int memberId = memberService.getMemberId(getTokenUserEmail(request));
		int memHelpCount = memhelpService.getMemHelpCount(memberId);
		log.info("memHelpCount: {}", memHelpCount);
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(memHelpCount);
		model.addAttribute("pagination", pagination);

		System.out.println("접속회원 ID : " + memberId);

//		List<MemhelpSearchByMemberId> memhelpSearchByMemberId = memhelpService.searchMemhelp(memberId);
		List<MemhelpSearchByMemberId> memhelpSearchByMemberId = memhelpService.searchPagingMemhelp(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), memberId);

		System.out.println("접속한 회원의 문의사항 리스트 : " + memhelpSearchByMemberId);

		model.addAttribute("memhelpList", memhelpSearchByMemberId);
		return "member/memhelp_view:: memberTable";
	}
	
	@Operation(summary = "회원이 등록한 문의사항의 Id로 문의사항 조회 -> 관리자 답변 달리면 답변 확인 가능")
	@GetMapping("/search/{memhelpNum}")
	public String getMemberhelpById(@PathVariable("memhelpNum") int memHelpNum,
									HttpServletRequest request, Model model) {
		System.out.println("pathvariable로 넘겨준 memhelpNum :" +memHelpNum);
		
		// jwt 토큰에서 멤버 정보 불러오기
		int memberId = memberService.getMemberId(getTokenUserEmail(request));
		
		Memhelp searchMemhelpById = memhelpService.searchMemhelpByMemhelpId(memHelpNum, memberId);
		
		System.out.println("해당하는 문의사항 : " + searchMemhelpById);
		
		model.addAttribute("memHelp", searchMemhelpById);
		
		// 관리자 답변이 달렸을 경우 로직 처리
		int checkAdminReply = Integer.parseInt(searchMemhelpById.getMemHelpState());
		
		System.out.println("관리자 답변 상태 : " + checkAdminReply);
		
		// 관리자의 답변이 있을 경우
		if(checkAdminReply!=0&checkAdminReply==1) {
			System.out.println("있다!!");
			
			Reply reply = replyService.searchReplyOfMemhelp(memHelpNum);
			
			System.out.println("유저가 자기 문의에 찾은 관리자 답변 : " + reply);
			
			// view에 th:if조건문을 통해서 관리자 답변 데이터 뿌려주기
			model.addAttribute("adminReply", reply);
		}
		
		
		return "member/memhelp_detail_view";
	}
	
	@Operation(summary = "회원별 문의사항 등록 view")
	@GetMapping("/insert")
	public String insertMemberhelp(HttpServletRequest request, Model model) {
		MemhelpInsertDto memhelp = new MemhelpInsertDto();
		String memberEmail = getTokenUserEmail(request);
		System.out.println("memberEmail="+memberEmail);
		model.addAttribute("memhelp", memhelp);
		model.addAttribute("memberEmail", memberEmail);
		return "member/memhelp_insert";
	}
	
	@Operation(summary = "회원별 문의사항 등록")
	@PostMapping("/insert")
	public String insertMemberhelp(@ModelAttribute("memhelp") MemhelpInsertDto memhelp, HttpServletRequest request, 
								   @RequestParam("memHelpDirPath") MultipartFile file, Model model) {
		System.out.println("프론트에서 신청버튼 눌러서 보낸 값" + memhelp);
		
		int memberId = memberService.getMemberId(getTokenUserEmail(request));
		
		Memhelp insertMemhelp = new Memhelp();
		
		Date now = new Date();
		
		insertMemhelp.setMemHelpNum(0);
		insertMemhelp.setMemHelpTitle(memhelp.getMemHelpTitle());
		insertMemhelp.setMemHelpContent(memhelp.getMemHelpContent());
		
		// 파일 경로를 넣는걸로 변경
		
//		insertMemhelp.setMemHelpFile(memhelp.getMemHelpFile());
		
		System.out.println("사진이 null이여야함 데이터 확인 : " + file.getOriginalFilename());
		
		String file_name = file.getOriginalFilename();
		
		if(file_name!=null&!file_name.equals("")) {
			String directoryPath = System.getProperty("user.dir") + "/src/main/resources/static/upload/";
			if (!new File(directoryPath).exists()) {
				new File(directoryPath).mkdirs();
			}

			UUID uuid = UUID.randomUUID();
			String fileName = uuid.toString() + "_" + file.getOriginalFilename();
			File saveFile = new File(directoryPath, fileName);
			
			System.out.println("파일 저장 이름은 : " + fileName);

			try {
				file.transferTo(saveFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			insertMemhelp.setMemHelpFile("/upload/" + fileName);
		}
		else {
			insertMemhelp.setMemHelpFile(file_name);
		}
		
		insertMemhelp.setMemHelpState("0");
		insertMemhelp.setMemHelpDate(new java.sql.Date(now.getTime()));
		insertMemhelp.setMemberId(memberId);
		insertMemhelp.setMemCategoryNum(memhelp.getMemCategoryNum());
		
		System.out.println("들어갈 memhelp 내용 : " + insertMemhelp);

		memhelpService.insertMemhelp(insertMemhelp);
		
		return "redirect:/memhelp";
	}
}
