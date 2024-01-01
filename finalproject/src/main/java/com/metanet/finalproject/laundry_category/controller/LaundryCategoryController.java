package com.metanet.finalproject.laundry_category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.metanet.finalproject.laundry_category.model.LaundryCategory;
import com.metanet.finalproject.laundry_category.service.ILaundryCategoryService;

@Controller
public class LaundryCategoryController {
	
	@Autowired
	ILaundryCategoryService laundryCategoryService;
	
	// 카테고리 전체 조회
	@GetMapping("/laundry/category")
	public String getLaundryCategory(Model model) {
		List<LaundryCategory> getLaundryCategory = laundryCategoryService.getLaundryCategory();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("세탁물 카테고리 전체 조회 : " + getLaundryCategory);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		return "member/home";
	}
	
	// 카테고리 조회
	@GetMapping("/laundry/category/{laundryCategoryId}")
	public String getLaundryCategory(Model model, @PathVariable int laundryCategoryId) {
		LaundryCategory getLaundryCategoryId = laundryCategoryService.getLaundryCategory(laundryCategoryId);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("세탁물 카테고리 조회 : " + getLaundryCategoryId);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>");
		return "member/home";
	}
	
}
