package com.metanet.finalproject.laundry.controller;

import java.util.List;

import com.metanet.finalproject.paging.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.metanet.finalproject.laundry.model.Laundry;
import com.metanet.finalproject.laundry.service.ILaundryService;
import com.metanet.finalproject.laundry_category.model.LaundryCategory;
import com.metanet.finalproject.laundry_category.service.ILaundryCategoryService;
import com.metanet.finalproject.laundry_category.service.LaundryCategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Controller
@Slf4j
@RequestMapping("/admin/laundry")
@Tag(name = "Laundry", description = "세탁물 관리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-37-210-134.ap-northeast-2.compute.amazonaws.com:8888",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class AdminLaundryController {
	
	@Autowired
	ILaundryService laundryService;
	
	@Autowired
	ILaundryCategoryService laundryCategoryService;
	
	// 세탁물 전체 조회
	@Operation(summary = "세탁물 전체 조회")
	@GetMapping("")
	public String getLaundry(Model model) {
//		List<Laundry> laundrys = laundryService.getLaundry();
		List<Laundry> laundrys = laundryService.getPagingLaundryCategory(0,10, 1);
		List<LaundryCategory> laundryCategorys = laundryCategoryService.getLaundryCategory();
		int laundryCount = laundryService.getLaundryCount(1);
		log.info("laundryCount: {}", laundryCount);
		Pagination pagination = new Pagination(1, 10, 10);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(laundryCount);
		model.addAttribute("pagination", pagination);

		model.addAttribute("laundrys",laundrys);
		model.addAttribute("laundryCategorys",laundryCategorys);

		return "admin/adminLaundry";
	}

	@Operation(summary = "세탁물 비동기 조회")
	@GetMapping("/async")
	public String getLaundryAsync(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
								  @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
								  @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
								  @RequestParam(value = "categoryId", required = false, defaultValue = "1") int categoryId,
								  Model model) {
//		List<Laundry> laundrys = laundryService.getLaundry();
		log.info("categoryId: {}", categoryId);
		List<LaundryCategory> laundryCategorys = laundryCategoryService.getLaundryCategory();
		int laundryCount = laundryService.getLaundryCount(categoryId);
		log.info("laundryCount: {}", laundryCount);
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(laundryCount);
		model.addAttribute("pagination", pagination);
		List<Laundry> laundrys = laundryService.getPagingLaundryCategory(pagination.getFirstRecordIndex(),pagination.getLastRecordIndex(), categoryId);
		model.addAttribute("laundrys",laundrys);
		model.addAttribute("laundryCategorys",laundryCategorys);

		return "admin/adminLaundry:: memberTable";
	}
	
	// 세탁물 카테고리별 조회
	// return : JSON
	@Operation(summary = "세탁물 카테고리별 조회")
	@GetMapping("/{laundryCategoryId}")
	public String getLaundryCategory(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
									 @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
									 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
									 Model model, @PathVariable int laundryCategoryId) {
		List<LaundryCategory> laundryCategorys = laundryCategoryService.getLaundryCategory();

		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		int laundryCount = laundryService.getLaundryCount(laundryCategoryId);
		log.info("laundryCount: {}", laundryCount);
		pagination.setTotalRecordCount(laundryCount);
		model.addAttribute("pagination", pagination);

		List<Laundry> laundrys = laundryService.getPagingLaundryCategory(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), laundryCategoryId);
		model.addAttribute("laundrys",laundrys);
		model.addAttribute("laundryCategorys",laundryCategorys);
		return "admin/adminLaundry:: memberTable";
	}
	
}
