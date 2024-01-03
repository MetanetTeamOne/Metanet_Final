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

@Controller
@RequestMapping("/member/address")
public class AddressController {
	
	@Autowired IAddressService addressService;

	// 사용자 주소 조회	
	@GetMapping("")
	public String getAddress(Model model) {
		List<Address> getAddress = addressService.getAddress(1);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("사용자 주소 조회 : " + getAddress);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		return "member/address_view";
	}
	
	// 사용자 주소 입력
	@GetMapping("/insert")
	public String insetAddress(Model model) {
		return "member/address_insert";
	}
	
	@PostMapping("/insert")
	public String insetAddress(Model model, Address address) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("사용자 주소 입력 : " + address);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
//		addressService.insertAddress(address);
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
	
	@PostMapping("/delete")
	public String deleteAddress(Model model, Address address) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("사용자 주소 삭제 : "+ address);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		addressService.deleteAddress(address);
		return "redirect:/member/address";
	}
}
