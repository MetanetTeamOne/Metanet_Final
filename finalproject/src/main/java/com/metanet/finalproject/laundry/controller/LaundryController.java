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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@RequestMapping("/laundry")
@Tag(name = "Laundry", description = "세탁물 관리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-39-151-127.ap-northeast-2.compute.amazonaws.com:8888/",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*")
public class LaundryController {
	
	@Autowired
	ILaundryService laundryService;
	
	// 세탁물 전체 조회
	@Operation(summary = "세탁물 전체 조회")
	@GetMapping("")
	public String getLaundry(Model model) {
		List<Laundry> getLaundry = laundryService.getLaundry();
		
		return "member/home";
	}
	
	// 세탁물 카테고리별 조회
	// return : JSON
	@Operation(summary = "세탁물 카테고리별 조회")
	@GetMapping("/{laundryCategoryId}")
	@ResponseBody
	public List<Laundry> getLaundryCategory(Model model, @PathVariable int laundryCategoryId) {
		List<Laundry> getLaundryCategory = laundryService.getLaundryCategory(laundryCategoryId);
		
		return getLaundryCategory;
	}
	
}
