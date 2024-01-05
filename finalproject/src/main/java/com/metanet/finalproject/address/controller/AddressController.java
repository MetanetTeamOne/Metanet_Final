package com.metanet.finalproject.address.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/member/address")
public class AddressController {
	
	@Autowired IAddressService addressService;
	
	@Autowired IMemberService memberService;

	@Autowired
    JwtTokenProvider jwtTokenProvider;
    
	//	Header에서 Token으로 사용자 이메일 획득
	private String getTokenUserEmail(HttpServletRequest request) {
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
	

	@GetMapping("")
	public String getAddress(HttpServletRequest request, Model model) {
		String userEmail = getTokenUserEmail(request);
		Member member = memberService.selectMember(userEmail);
		List<Address> addressList = addressService.getAddress(member.getMemberId());
		model.addAttribute("addressList", addressList);
		return "member/address_view";
	}
	
	// 사용자 주소 입력
	@GetMapping("/insert")
	public String insetAddress(Model model) {
		return "member/address_insert";
	}
	
	@PostMapping("/insert")
	public String insetAddress(HttpServletRequest request, Model model, Address address) {
		String userEmail = getTokenUserEmail(request);
		Member member = memberService.selectMember(userEmail);
		address.setMemberId(member.getMemberId());
		address.setAddressCategory("3");
		addressService.insertAddress(address);

		return "redirect:/member/address";
	}
	
	// 사용자 주소 수정
	@GetMapping("/update")
	public String updateAddress(Model model) {
		return "member/address_update";
	}
	
	@PostMapping("/update")
	public String updateAddress(Model model, Address address) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("사용자 주소 수정 : "+ address);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		addressService.updateAddress(address);
		return "redirect:/member/address";
	}
	
	@PostMapping("/delete/{addressId}")
	public String deleteAddress(@PathVariable int addressId, Model model, Address address) {
//		addressService.deleteAddress(addressId);
		return "redirect:/member/address";
	}
}
