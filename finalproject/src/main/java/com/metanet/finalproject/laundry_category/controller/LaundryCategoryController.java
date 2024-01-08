package com.metanet.finalproject.laundry_category.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.metanet.finalproject.laundry_category.model.LaundryCategory;
import com.metanet.finalproject.laundry_category.service.ILaundryCategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/laundry/category")
@Tag(name = "laundry", description = "세탁물 카테고리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-39-151-127.ap-northeast-2.compute.amazonaws.com:8888/",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*")
public class LaundryCategoryController {
	
	@Autowired
	ILaundryCategoryService laundryCategoryService;
	
	// 카테고리 전체 조회
	@GetMapping("")
	public String getLaundryCategory(Model model) {
		List<LaundryCategory> getLaundryCategory = laundryCategoryService.getLaundryCategory();
		
		LaundryCategory g = new LaundryCategory();

		return "member/home";
	}
	
	// 카테고리 조회
	@GetMapping("/{laundryCategoryId}")
	public String getLaundryCategory(Model model, @PathVariable int laundryCategoryId) {
		LaundryCategory getLaundryCategoryId = laundryCategoryService.getLaundryCategory(laundryCategoryId);
		return "member/home";
	}
	
}
