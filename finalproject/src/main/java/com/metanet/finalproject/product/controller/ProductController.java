package com.metanet.finalproject.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;

@Controller
@RequestMapping("/product")
public class ProductController {
	// 상품 검색
//	@Operation(summary="일반 회원 상품 검색")
//	@GetMa
	
	// 관리자용 : 상품 등록
	@Operation(summary="관리자용 상품 검색")
	@GetMapping("/insert/{productId}")
	public String insertProduct() {
		return "product_insert";
	}
}
