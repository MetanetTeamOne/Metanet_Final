package com.metanet.finalproject.laundry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.metanet.finalproject.laundry.model.Laundry;
import com.metanet.finalproject.laundry.service.ILaundryService;
import com.metanet.finalproject.laundry_category.model.LaundryCategory;
import com.metanet.finalproject.laundry_category.service.ILaundryCategoryService;
import com.metanet.finalproject.laundry_category.service.LaundryCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/admin/laundry")
@Tag(name = "Laundry", description = "세탁물 관리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-39-151-127.ap-northeast-2.compute.amazonaws.com:8888/",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*")
public class adminLaundryController {
	
	@Autowired
	ILaundryService laundryService;
	
	@Autowired
	ILaundryCategoryService laundryCategoryService;
	
	// 세탁물 전체 조회
	@Operation(summary = "세탁물 전체 조회")
	@GetMapping("")
	public String getLaundry(Model model) {
		List<Laundry> laundrys = laundryService.getLaundry();
		List<LaundryCategory> laundryCategorys = laundryCategoryService.getLaundryCategory();

		model.addAttribute("laundrys",laundrys);
		model.addAttribute("laundryCategorys",laundryCategorys);

		return "admin/adminLaundry";
	}
	
	// 세탁물 카테고리별 조회
	// return : JSON
	@Operation(summary = "세탁물 카테고리별 조회")
	@GetMapping("/{laundryCategoryId}")
	public String getLaundryCategory(Model model, @PathVariable int laundryCategoryId) {
		List<Laundry> laundrys = laundryService.getLaundryCategory(laundryCategoryId);
		List<LaundryCategory> laundryCategorys = laundryCategoryService.getLaundryCategory();
		
		model.addAttribute("laundrys",laundrys);
		model.addAttribute("laundryCategorys",laundryCategorys);
		return "admin/adminLaundry:: memberTable";
	}
	
}
