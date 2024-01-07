package com.metanet.finalproject.address.controller;

import java.util.List;

import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.address.service.IAddressService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/member/address")
@Slf4j
@Tag(name = "Address", description = "주소 관리 API")
public class AddressController {
	
	@Autowired IAddressService addressService;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	IMemberService memberService;

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
	@Operation(summary = "사용자 주소 조회")
	@GetMapping("")
	public String getAddress(Model model) {
//		List<Address> getAddress = addressService.getAddress(1);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
//		System.out.println("사용자 주소 조회 : " + getAddress);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		return "member/address_view";
	}
	
	@Operation(summary = "사용자 주소 입력 view")
	@GetMapping("/insert")
	public String insertAddress(Model model) {
		Address address = new Address();
		model.addAttribute("insertAddress", address); // 빈 Address 객체 만들어서 안보내면 검증 안됨
		return "member/address_insert";
	}
	
	@Operation(summary = "사용자 주소 입력")
	@PostMapping("/insert")
	public String insertAddress(@Valid @ModelAttribute("address") Address address, BindingResult result) {

		if (result.hasErrors()) { //form에서 검증실패한 필드가 있으면
			log.info("errors: {}", result);
			return "member/address_insert"; //result를 가지고 다시 입력 폼으로 돌아감
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("사용자 주소 입력 : " + address);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		addressService.insertAddress(address);
		return "redirect:/member/address";
	}

	@Operation(summary = "사용자 주소 수정 view")
	@GetMapping("/update")
	public String updateAddress(Model model, HttpServletRequest request) {
		int memberId = memberService.selectMember(getTokenUserEmail(request)).getMemberId();
		Address address = addressService.getAddress(memberId);

		model.addAttribute("updateAddress", address);
		return "member/address_update";
	}
	
	@Operation(summary = "사용자 주소 수정")
	@PostMapping("/update")
	public String updateAddress(@Valid @ModelAttribute("address") Address address, BindingResult result) {

		if (result.hasErrors()) {
			log.info("errors: {}", result);
			return "member/address_update";
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("사용자 주소 수정 : "+ address);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
//		log.info("update address: {}", address);
		addressService.updateAddress(address);
		return "redirect:/member/address";
	}
	
	@Operation(summary = "사용자 주소 삭제")
	@PostMapping("/delete")
	public String deleteAddress(Model model, Address address) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("사용자 주소 삭제 : "+ address);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
//		addressService.deleteAddress(address);
		return "redirect:/member/address";
	}
}
