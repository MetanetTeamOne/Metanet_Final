package com.metanet.finalproject.address.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.address.service.IAddressService;

@Controller
public class AddressController {
	
	@Autowired IAddressService addressService;

	// 사용자 주소 조회	
	@GetMapping("/member/address/{memberId}")
	public String getAddress(Model model, @PathVariable int memberId) {
		List<Address> getAddress = addressService.getAddress(memberId);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("사용자 주소 조회 : " + getAddress);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		return "member/home";
	}
	
	// 사용자 주소 입력
	@GetMapping("/member/address/insert")
	public String insetAddress(Model model) {
		return "member/home";
	}
	
	@PostMapping("/member/address/insert")
	public String insetAddress(Model model, Address address) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("사용자 주소 입력 : " + address);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		addressService.insertAddress(address);
		return "member/home";
	}
	
	// 사용자 주소 수정
	@GetMapping("/member/address/update")
	public String updateAddress(Model model) {
		return "member/home";
	}
	@PostMapping("/member/address/update")
	public String updateAddress(Model model, Address address) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("사용자 주소 수정 : "+ address);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		addressService.updateAddress(address);
		return "member/home";
	}
	
	@PostMapping("/member/address/delete")
	public String deleteAddress(Model model, Address address) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("사용자 주소 삭제 : "+ address);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		addressService.deleteAddress(address);
		return "member/home";
	}
}
