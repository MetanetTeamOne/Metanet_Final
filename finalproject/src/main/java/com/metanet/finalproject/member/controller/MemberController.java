package com.metanet.finalproject.member.controller;

import java.sql.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.model.MemberDeleteDto;
import com.metanet.finalproject.member.model.MemberInsertDto;
import com.metanet.finalproject.member.model.MemberPasswordDto;
import com.metanet.finalproject.member.model.MemberUpdateDto;
import com.metanet.finalproject.member.model.ResponseDto;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.member.service.TestService;
import com.metanet.finalproject.paging.Pagination;
import com.metanet.finalproject.pay.model.Pay;
import com.metanet.finalproject.pay.service.Bootpay;
import com.metanet.finalproject.pay.service.IPayService;
import com.metanet.finalproject.role.model.Role;
import com.metanet.finalproject.role.repository.IRoleRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Slf4j
@Controller
@RequestMapping("/member")
@Tag(name = "Member", description = "회원 관리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-43-201-12-132.ap-northeast-2.compute.amazonaws.com:8888",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class MemberController {

    @Autowired
    IMemberService memberService;

    @Autowired
    IAddressService addressService;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    IPayService payService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    TestService smsService;

    // Header에서 Token으로 사용자 이메일 획득
    private String getTokenUserEmail(HttpServletRequest request) {
        log.info("이메일로 토큰 받는중...");
        String token = "";

        try {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return jwtTokenProvider.getUserId(token);
    }

    @Operation(summary = "회원 정보 조회")
    @GetMapping("")
    public String getMember(Model model, HttpServletRequest request) {
        Member emptyMember = new Member();
        try {

            String userEmail = getTokenUserEmail(request);
            if (userEmail == null || userEmail.isEmpty()) {
                model.addAttribute("dto", emptyMember);
                return "member/login";
            }

            Member member = memberService.selectMember(userEmail);

            if (member == null) {
                model.addAttribute("dto", emptyMember);
                return "member/login";
            } else {
                model.addAttribute("member", member);
                return "member/member_view";
            }
        } catch (Exception e) {
            model.addAttribute("dto", emptyMember);
            return "member/login";
        }
    }

    @Operation(summary = "회원 가입 view")
    @GetMapping("/insert")
    public String insertMember(HttpSession session, Model model) {
        MemberInsertDto member = new MemberInsertDto();
        /*
         * String csrfToken = UUID.randomUUID().toString();
         * session.setAttribute("csrfToken", csrfToken);
         * log.info("/member/insert, GET {}", csrfToken);
         */
        model.addAttribute("dto", member);
        return "member/signup";
    }

    @Operation(summary = "회원 가입")
    @PostMapping("/insert")
    public String insertMember(@Valid @ModelAttribute("dto") MemberInsertDto dto, BindingResult result,
                               HttpSession session, Model model) {
        log.info("회원가입 진행중...");
        if (result.hasErrors()) {
            log.info("errors: {}", result);
            return "member/signup";
        }
        if (!dto.getMemberPassword().equals(dto.getReMemberPassword())) {
            result.rejectValue("memberPassword", null, "비밀번호가 일치하지 않습니다");
            log.info("errors: {}", result);
            return "member/signup";
        }
        Member phoneMember = memberService.searchMemberByPhonenumber(dto.getMemberPhoneNumber());
        if (phoneMember != null) {
            result.rejectValue("memberPhoneNumber", null, "같은 휴대폰번호가 있습니다.");
            log.info("errors: {}", result);
            return "member/signup";
        }
        Member findMember = memberService.selectMember(dto.getMemberEmail());

        Member member = new Member();
        Address address = new Address();
        log.info("dto: {}", dto.getMemberEmail());
        log.info("아이디 {}", findMember);

        if (findMember != null) {
            log.info("같은 아이디가 있습니다");
            model.addAttribute("member", dto);
            return "redirect:/member/signup";
        }
        /*
         * String sessionToken = (String) session.getAttribute("csrfToken");
         * if(csrfToken==null || !csrfToken.equals(sessionToken)) { throw new
         * RuntimeException("CSRF Token Error."); }
         */

        try {
            // 회원 정보 저장
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

//			Role role = new Role();
//			role.setMemberId(memberId);
//			if(dto.getMemberEmail().equals("king@king")) {
//				//본사
//				role.setRoleName("ROLE_KING");
//				roleRepository.insertRole(role);
//			} else if(dto.getMemberEmail().equals("1dmin@admin")) {
//				//지점
//				role.setRoleName("ROLE_ADMIN");
//				roleRepository.insertRole(role);
//			} else {
//				//사용자
//				role.setRoleName("ROLE_USER");
//				roleRepository.insertRole(role);
//			}


            // 기존 코드
            // 권한 부여
            Role role = new Role();
            role.setMemberId(memberId);
            role.setRoleName("ROLE_USER");
            roleRepository.insertRole(role);

            // 주소 저장
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
        // 수정 필요
        session.invalidate();
        return "redirect:/member/signup_ok";
    }

    @ResponseBody
    @GetMapping("/emailCheck")
    public ResponseDto<?> check(String email) {
        Member member = memberService.selectMember(email);
        if (email == null || email.isEmpty()) {
            return new ResponseDto<>(-1, "이메일을 입력해주세요.", null);
        }
        if (member != null) {
            return new ResponseDto<>(1, "같은 이메일이 존재합니다.", false);
        } else {
            return new ResponseDto<>(1, "회원가입 가능한 이메일입니다.", true);
        }
    }

    @Operation(summary = "회원 가입 완료 view")
    @GetMapping("/signup_ok")
    public String insertOkMember(HttpServletRequest request, Model model) {
//    	Member member = memberService.selectMember(getTokenUserEmail(request)); 회원가입 완료 페이지는 회원정보 안뿌리고 그냥 가입확인만 하기로함
//    	model.addAttribute("member",member);
        return "member/signup_ok";
    }

    @Operation(summary = "회원 정보 수정 view")
    @GetMapping("/update")
    public String updateMember(Model model, HttpServletRequest request) {
        Member member = memberService.selectMember(getTokenUserEmail(request));
        MemberUpdateDto dto = new MemberUpdateDto();
        dto.setMemberEmail(member.getMemberEmail());
        dto.setMemberName(member.getMemberName());
        dto.setMemberPhoneNumber(member.getMemberPhoneNumber());
        log.info("member: {}", member);
        model.addAttribute("updateMember", dto);
        return "member/member_update";
    }

    @Operation(summary = "회원 정보 수정")
    @PostMapping("/update")
    public String updateMember(@Valid @ModelAttribute("updateMember") MemberUpdateDto dto, BindingResult result,
                               HttpServletRequest request) {

        Member member = memberService.selectMember(getTokenUserEmail(request));
        if (result.hasErrors()) {
            return "member/member_update";
        }
        member.setMemberName(dto.getMemberName());
        member.setMemberPhoneNumber(dto.getMemberPhoneNumber());
        log.info("dto: {}", dto);
        memberService.updateMember(member, getTokenUserEmail(request));
        return "redirect:/member";
    }

    @Operation(summary = "회원 비밀번호 수정 view")
    @GetMapping("/password")
    public String updatePasswordMember(HttpServletRequest request, Model model) {
        Member member = memberService.selectMember(getTokenUserEmail(request));
        MemberPasswordDto dto = new MemberPasswordDto();
        dto.setMemberEmail(member.getMemberEmail());
        model.addAttribute("dto", dto);
        return "member/member_password";
    }

    @Operation(summary = "회원 비밀번호 수정")
    @PostMapping("/password")
    public String updatePasswordMember(@Valid @ModelAttribute("dto") MemberPasswordDto dto, BindingResult result,
                                       HttpServletRequest request) {
        if (result.hasErrors()) {
            return "member/member_password";
        }
        Member member = memberService.selectMember(getTokenUserEmail(request));
        log.info("member: {}", member);
        if (!passwordEncoder.matches(dto.getMemberPassword(), member.getMemberPassword())) { // 원래 비밀번호와 폼에서 입력받은 비밀번호
            // 비교
            result.rejectValue("memberPassword", null, "잘못된 비밀번호를 입력했습니다.");
            return "member/member_password";
        }
        /*
         * if(member.getMemberPassword() == null &&
         * !passwordEncoder.matches(member.getMemberPassword(),
         * dbMember.getMemberPassword())) { model.addFlashAttribute("message",
         * "비밀번호가 맞지 않습니다."); return "redirect:/member/password"; }
         */
        member.setMemberPassword(passwordEncoder.encode(dto.getNewPassword()));
        memberService.updateMember(member, dto.getMemberEmail());
        return "redirect:/member";

    }

    @Operation(summary = "회원 삭제 view")
    @GetMapping("/delete")
    public String memberDeleteForm(HttpServletRequest request, Model model) {
        String email = getTokenUserEmail(request);
        MemberDeleteDto dto = new MemberDeleteDto();
        dto.setMemberEmail(email);
        model.addAttribute("dto", dto);
        return "member/member_delete";
    }

    @Operation(summary = "회원 삭제")
    @PostMapping("/delete")
    public String deleteMember(@Valid @ModelAttribute("dto") MemberDeleteDto dto, BindingResult result,
                               HttpServletRequest request, HttpServletResponse response) {
        if (result.hasErrors()) {
            log.info("필드에러 발생");
            return "member/member_delete";
        }
        Member member = memberService.selectMember(getTokenUserEmail(request));
        if (!passwordEncoder.matches(dto.getMemberPassword(), member.getMemberPassword())) {
            log.info("비밀번호가 일치하지 않습니다.");
            result.rejectValue("memberPassword", null, "비밀번호가 일치하지 않습니다.");
//			model.addAttribute("message", "비밀번호가 맞지 않습니다.");
            return "member/member_delete";
        }

        memberService.deleteMember(member.getMemberEmail(), "0");
        log.info("삭제완료");

        return "redirect:/logout2";
    }

    @Operation(summary = "회원 구독 view")
    @GetMapping("/subscribe")
    public String selectSubscribe(HttpServletRequest request, Model model) {
        Member member = memberService.selectMember(getTokenUserEmail(request));
        model.addAttribute("member", member);
        return "member/subscribe_view";
    }

    @Operation(summary = "회원 구독 신청")
    @PostMapping("/subscribe")
    public String updateSubscribe(HttpServletRequest request, Member member, Model model) {
        member.setMemberEmail(getTokenUserEmail(request));
        //System.out.println("cardNumber"+member.getMemberCard());
        memberService.updateSubscribe(member);
        return "redirect:/member/subscribe";
    }

    @Operation(summary = "회원 구독 해제")
    @PostMapping("/subscribe/delete")
    public String DeleteSubscribe(HttpServletRequest request, Member member, Model model) throws Exception {

        if (member.getMemberSubscribe().equals("0")) {
            Bootpay bootpay = new Bootpay("65a160cc00c78a001d3460da", "w+lPLe/uLDoPOUXZxesF1bNUGpWPKgQ5n0dUtFmxwW8=");
            bootpay.getAccessToken();
            String billingKey = memberService.selectMember(getTokenUserEmail(request)).getMemberCard();
            try {
                HttpResponse res = bootpay.destroyBillingKey(billingKey);
                int statusCode = res.getStatusLine().getStatusCode();
                System.out.println("statusCode>>> " + statusCode);
                if (statusCode == 200) {
                    // 성공적인 응답 처리
                    System.out.println("destroyBillingKey success");

                    member.setMemberEmail(getTokenUserEmail(request));
                    memberService.updateSubscribe(member);

                    return "redirect:/member/subscribe";

                } else {
                    // 응답 본문을 문자열로 추출
                    String responseBody = EntityUtils.toString(res.getEntity());
                    System.out.println("responseBody: " + responseBody);

                    // JSON 문자열을 JsonObject로 변환
                    JsonParser jsonParser = new JsonParser();
                    JsonObject json = jsonParser.parse(responseBody).getAsJsonObject();

                    // 에러 코드 확인
                    if (json.has("status") && json.get("status").getAsInt() != 200) {
                        System.out.println("destroyBillingKey false: " + json);
                    } else {
                        System.out.println("Unknown error: " + responseBody);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return "redirect:/member/subscribe";
    }

    @GetMapping("/card")
    public String getCard(HttpServletRequest request, Model model) {
        Member member = memberService.selectMember(getTokenUserEmail(request));
        model.addAttribute("member", member);
        int payCount = payService.getPayCountByState(member.getMemberId(), "1");
        log.info("payCount: {}", payCount);
        Pagination pagination = new Pagination(1, 10, 10);
        log.info("pagination: {}", pagination);
        pagination.setTotalRecordCount(payCount);
        model.addAttribute("pagination", pagination);
//			List<Pay> pays = payService.getMemberPay(member.getMemberId());
        List<Pay> pays = payService.getPagingMemberPayByState(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), member.getMemberId(), "1");
        model.addAttribute("pays", pays);
        return "member/card_view";
    }

    @GetMapping("/card/async")
    public String getCardAsync(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
                               @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                               @RequestParam(value = "state", required = false, defaultValue = "1") String state,
                               HttpServletRequest request, Model model) {
        Member member = memberService.selectMember(getTokenUserEmail(request));
        model.addAttribute("member", member);
        int payCount = payService.getPayCountByState(member.getMemberId(), state);
        log.info("payCount: {}", payCount);

        Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
        log.info("pagination: {}", pagination);
        pagination.setTotalRecordCount(payCount);
        model.addAttribute("pagination", pagination);

        List<Pay> pays = payService.getPagingMemberPayByState(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), member.getMemberId(), state);
        model.addAttribute("pays", pays);
        log.info("list: {}", payService.getPagingMemberPayByState(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), member.getMemberId(), state));

        return "member/card_view:: memberTable";
    }


//	@GetMapping("/card")
//	public String getCard(Model model, String memberEmail) {
//  			if(memberEmail != null && !memberEmail.equals("")) {
//		Member member = memberService.selectMember(memberEmail);
//=======
//  	@GetMapping("/card")
//  	public String getCard(HttpServletRequest request, Model model, String memberEmail) {
//		Member member = memberService.selectMember(getTokenUserEmail(request));
//>>>>>>> 654511a Merge branch 'main' of https://github.com/MetanetTeamOne/Metanet_Final.git into main
//		model.addAttribute("member", member);
//		return "member/card_view";
//  			}else {
//  				return "member/login";
//  			}
//	}

//	@GetMapping("/card")
//	public String getCard(HttpServletRequest request, Model model, String memberEmail) {
//		Member member = memberService.selectMember(getTokenUserEmail(request));
//		model.addAttribute("member", member);
//		return "member/card_view";
////          }else {
////             return "member/login";
////          }
//	}

    // 카드 등록 폼
//    @GetMapping("/card/insert")
//    public String insertCard(HttpServletRequest request, Model model) {
////  			if(memberEmail != null && !memberEmail.equals("")) {
//        Member member = memberService.selectMember(getTokenUserEmail(request));
//        model.addAttribute("member", member);
//        return "member/card_insert";
////  			}else {
////  				return "member/login";
////  			}
//    }
//
//    // 카드 등록 처리
//    @PostMapping("/card/insert")
//    public String insertCard(Member member, Model model, String memberEmail) {
//        memberService.insertCard(memberEmail);
//        return "redirect:/member/card";
//    }
//
//    // 카드 해지 구현 필요
//    // 카드 등록 처리
//    @PostMapping("/card/delete")
//    public String deleteCard(Member member, Model model, String memberEmail) {
//        // 카드 해지 서비스 로직 필요
//        memberService.deleteCard(memberEmail);
////		model.addAttribute("member", member);
//
//        System.out.println("===카드 해지 완료===");
//        return "redirect:/member/card";
//    }


    // coolSMS 연결
    @PostMapping("/memberPhoneCheck")
    public @ResponseBody String memberPhoneCheck(@RequestParam(value = "to") String to) throws CoolsmsException {
        return smsService.PhoneNumberCheck(to);
    }
}