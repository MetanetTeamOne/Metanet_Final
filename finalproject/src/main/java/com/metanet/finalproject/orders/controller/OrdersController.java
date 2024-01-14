package com.metanet.finalproject.orders.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.metanet.finalproject.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.metanet.finalproject.address.model.Address;
import com.metanet.finalproject.address.service.IAddressService;
import com.metanet.finalproject.files.model.Files;
import com.metanet.finalproject.files.service.IFilesService;
import com.metanet.finalproject.jwt.JwtTokenProvider;
import com.metanet.finalproject.laundry.model.Laundry;
import com.metanet.finalproject.laundry.service.ILaundryService;
import com.metanet.finalproject.laundry_category.model.LaundryCategory;
import com.metanet.finalproject.laundry_category.service.ILaundryCategoryService;
import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.model.OrdersDetails;
import com.metanet.finalproject.orders.model.OrdersInsert;
import com.metanet.finalproject.orders.model.OrdersInsertList;
import com.metanet.finalproject.orders.service.IOrdersService;
import com.metanet.finalproject.pay.model.Pay;
import com.metanet.finalproject.pay.service.IPayService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@Tag(name = "Orders", description = "주문 관리 API")
@CrossOrigin(origins = { "http://localhost:8085", "http://ec2-3-39-151-127.ap-northeast-2.compute.amazonaws.com:8888/",
		"http://metawash.kro.kr:8888/" }, allowedHeaders = "*", allowCredentials = "true")
public class OrdersController {

//	@Value("${file.upload.directory}")
//	private String uploadDirectory;

	@Autowired
	IOrdersService ordersService;

	@Autowired
	IPayService payService;

	@Autowired
	IMemberService memberService;

	@Autowired
	IFilesService fileService;

	@Autowired
	IAddressService addressService;

	@Autowired
	ILaundryCategoryService laundryCategoryService;

	@Autowired
	ILaundryService laundryService;

	Files files;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	// Header에서 Token으로 사용자 이메일 획득
	private String getTokenUserEmail(HttpServletRequest request) {
		log.info("이메일로 토큰 받는중...");
		String token = "";

		try {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("token")) {
					token = cookie.getValue();
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}

		return jwtTokenProvider.getUserId(token);
	}

	@Operation(summary = "주문")
	@GetMapping("")
	public String getOrder(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
						   @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
						   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, HttpServletRequest request, Model model) {
		Member member = memberService.selectMember(getTokenUserEmail(request));
		int orderCount = ordersService.getOrderCount(1, member.getMemberId());
		log.info("orderCount: {}", orderCount);
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(orderCount);

		model.addAttribute("pagination", pagination);

//		List<OrdersDetails> orders = ordersService.searchMemOrder(member.getMemberId());
		List<OrdersDetails> orders = ordersService.searchPagingMemOrder(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), member.getMemberId());
		log.info("orders: {}", orders);
		if (member.getMemberSubscribe().equals("0")) {
			for (OrdersDetails order : orders) {
				order.setOrdersTotalPrice(order.getOrdersTotalPrice() + 2500);
			}
		}
		model.addAttribute("orders", orders);
		return "member/orders_view";
	}


	@Operation(summary = "주문")
	@GetMapping("/async")
	public String getOrderAsync(@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
						   @RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
						   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
						   @RequestParam(value = "month", required = false, defaultValue = "1") int month,
						   HttpServletRequest request, Model model) {

		Member member = memberService.selectMember(getTokenUserEmail(request));
		int orderCount = ordersService.getOrderCount(month, member.getMemberId());
		log.info("orderCount: {}", orderCount);
		Pagination pagination = new Pagination(currentPage, cntPerPage, pageSize);
		log.info("pagination: {}", pagination);
		pagination.setTotalRecordCount(orderCount);

		model.addAttribute("pagination", pagination);

//		List<OrdersDetails> orders = ordersService.searchMemOrder(member.getMemberId());
		List<OrdersDetails> orders = ordersService.searchPagingMemMonthOrder(pagination.getFirstRecordIndex(), pagination.getLastRecordIndex(), member.getMemberId(), month);
		log.info("orders: {}", orders);
		if (member.getMemberSubscribe().equals("0")) {
			for (OrdersDetails order : orders) {
				order.setOrdersTotalPrice(order.getOrdersTotalPrice() + 2500);
			}
		}
		model.addAttribute("orders", orders);
		return "member/orders_view:: memberTable";
	}
	
//	@Operation(summary = "회원별 주문 조회")
//	@GetMapping("/{memberId}")
//	@ResponseBody
//	public List<Orders> searchOrder(Model model, @PathVariable int memberId){
////	List<Orders> searchOrder(HttpServletRequest request){
////	String token = tokenProvider.resolveToken(request);
////  log.info("token {}",token); //권장
////    
////  Authentication auth = tokenProvider.getAuthentication(token);
////  log.info("principal {}, name {}, authorities{}", auth.getPrincipal(), auth.getName(), auth.getAuthorities());
//		Orders order = new Orders();
//		
//		
////		System.out.println(order.getWashId());
////		
////		System.out.println("111");
//		
//		return ordersService.searchOrder(memberId, -1);
//	}


	//	비동기
	@Operation(summary = "회원 회차별 주문 조회")
	@GetMapping("/month/{month}")
	public String searchMonthOrder(HttpServletRequest request, Model model, @PathVariable int month) {
		log.info("회원 월별 주문조회");
		log.info("month: {}", month);
		Member member = memberService.selectMember(getTokenUserEmail(request));
		Pagination pagination = new Pagination(1, 10, 10);
		int orderCount = ordersService.getOrderCount(month, member.getMemberId());
		pagination.setTotalRecordCount(orderCount);
		model.addAttribute("pagination", pagination);


//		List<OrdersDetails> orders = ordersService.searchMonthOrder(member.getMemberId(), month);
		List<OrdersDetails> orders = ordersService.searchPagingMemMonthOrder(1, 10, member.getMemberId(), month);
		if (member.getMemberSubscribe().equals("0")) {
			for (OrdersDetails order : orders) {
				order.setOrdersTotalPrice(order.getOrdersTotalPrice() + 2500);
			}
		}
		model.addAttribute("orders", orders);
		return "member/orders_view :: memberTable";
	}

//	@Operation(summary = "회원 개월별 주문 조회")
//	@GetMapping("/{memberId}/{washId}")
//	@ResponseBody
//	public List<Orders> searchOrder(Model model, @PathVariable int memberId, @PathVariable int washId){
////		System.out.println(memberId + " : m w : " + washId);
////		log.info("memberId : {}, washId : {}", memberId, washId);
//		return ordersService.searchOrder(memberId, washId);
//	}

	@Operation(summary = "주문 입력 view")
	@GetMapping("/insert")
	public String insertOrder(Model model, HttpServletRequest request) {
		// try {
		Member member = memberService.selectMember(getTokenUserEmail(request));
		// if (member == null) {
		// return "member/login";
		// }
		// else {
		int count = ordersService.countOrder(member.getMemberId());
		int washId = count + 1;
		Address addressList = addressService.getAddress(member.getMemberId());
		List<LaundryCategory> laundryCategoryList = laundryCategoryService.getLaundryCategory();
		System.out.println("laundryCategoryList>>>>" + laundryCategoryList);
		// System.out.println("laundryCategoryList 길이>>>"+laundryCategoryList.size());
		List<Laundry> laundryList = laundryService.getLaundryCategory(1);
		System.out.println("List<Laundry> laundryList>>>>>>>>>>>>>>>>" + laundryList);
		// model.addAttribute("ordersListSize", ordersListSize);
		model.addAttribute("washId", washId);
		model.addAttribute("member", member);
		model.addAttribute("addressList", addressList);
		model.addAttribute("laundryCategoryList", laundryCategoryList);
		model.addAttribute("laundryList", laundryList);
		return "member/orders_insert";
		// }
		// } catch(Exception e){
		// return "member/login";
		// }

	}

	@Operation(summary = "주문 입력")
	@PostMapping("/insert")
	public String insertOrder(Model model, @ModelAttribute(value = "OrdersInsertList") OrdersInsertList ordersList,
			HttpServletRequest request, @RequestParam("ordersDirPath") MultipartFile file) throws Exception {
		Member member = memberService.selectMember(getTokenUserEmail(request));
		int memberId = member.getMemberId();
		int total = 0;
		int washId = ordersService.searchMaxWashId(memberId);

		Orders order = new Orders();
		order.setMemberId(memberId);
		order.setOrdersCheckDate(ordersList.getOrdersCheckDate());
		order.setOrdersComment(ordersList.getOrdersComment());
		order.setOrdersStatus(ordersList.getOrdersStatus());
		order.setWashId(washId);
		for (OrdersInsert ord : ordersList.getOrderList()) {
			Laundry laundry = laundryService.getLaundryId(ord.getLaundryName());
			order.setLaundryId(laundry.getLaundryId());
			order.setOrdersCount(ord.getOrdersCount());
			order.setOrdersPrice(laundry.getLaundryPrice() * ord.getOrdersCount());
			total += laundry.getLaundryPrice() * ord.getOrdersCount();
			String directoryPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\upload\\";
			if (!new File(directoryPath).exists()) {
				new File(directoryPath).mkdirs();
			}

			UUID uuid = UUID.randomUUID();
			String fileName = uuid.toString() + "_" + file.getOriginalFilename();
			File saveFile = new File(directoryPath, fileName);

			try {
				file.transferTo(saveFile);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

			order.setOrdersDirPath("/upload/" + fileName);
			
			ordersService.insertOrder(order);
		}

		Pay pay = new Pay();
		if (member.getMemberSubscribe().equals("0")) {
			pay.setPayDelivery(2500);
			pay.setPayMoney(total + 2500);
		} else {
			pay.setPayDelivery(0);
			pay.setPayMoney(total);
		}
		pay.setPayState("1");
		pay.setWashId(washId);
		pay.setMemberId(memberId);

		payService.insertPay(pay);

		return "redirect:/orders/insertok";

	}

	@Operation(summary = "회원별 주문 건수 조회")
	@GetMapping("/count/{memberId}")
	@ResponseBody
	public int countOrder(@PathVariable int memberId) {
		return ordersService.countOrder(memberId);
	}

	@Operation(summary = "전체 주문 건수 조회")
	@GetMapping("/count")
	@ResponseBody
	public int countOrder() {
		return ordersService.countOrder();
	}

//	@Operation(summary = "10분 간격 새로운 주문 건수 조회")
//	@GetMapping("/newcount")
//	@ResponseBody
//	public int countNewOrder() {
//		return ordersService.countNewOrder();
//	}

	/*
	 * @Operation(summary = "주문 입력")
	 * 
	 * @PostMapping("/insert") public String insertOrder(Model model, Principal
	 * principal) throws IOException { System.out.println(orders);
	 * 
	 * if (!file.isEmpty()) { orders.setOrdersImageData(file.getBytes());
	 * ordersService.insertOrder(orders); } else { orders.setOrdersImageData(null);
	 * ordersService.insertOrder(orders); }
	 * System.out.println("=============================");
	 * 
	 * return "redirect:/orders/insertok"; }
	 */

	@Operation(summary = "주문 완료 view")
	@GetMapping("/insertok")
	public String insertOkOrder(Model model) {
//		System.out.println("======================");
		return "member/orders_insert_ok";
	}

	@Operation(summary = "주문 수정 view")
	@GetMapping("/update/{washId}")
	public String updateOrder(Model model, @PathVariable int washId) {
		List<Orders> orders = ordersService.searchOrderId(washId);
		model.addAttribute("orders", orders);
		List<Laundry> laundrys = laundryService.getLaundry();
		model.addAttribute("laundrys", laundrys);
		return "member/orders_update";
	}
//	
//	@Operation(summary = "주문 수정")
//	@PostMapping("/update")
//	public String updateOrder(Model model, Orders orders) {
////		System.out.println(orders);
//		ordersService.updateOrder(orders);
//		return "member/mypage_order";
//	}

	@Operation(summary = "주문 삭제")
	@PostMapping("/delete/{washId}")
	public String deleteWashIdOrder(@PathVariable("washId") int washId) {
		ordersService.deleteWashOrder(washId);
		Pay pay = new Pay();
		pay.setPayState("2");
		pay.setWashId(washId);
		payService.updatePay(pay);
		return "redirect:/orders";
	}

	@Operation(summary = "주문 상세 삭제")
	@PostMapping("/delete/{ordersId}/{washId}")
	public String deleteOrder(HttpServletRequest request, @PathVariable("ordersId") int ordersId,
			@PathVariable("washId") int washId) {
		ordersService.deleteOrder(ordersId, washId);
		// 주문 취소 시 결제 총금액 및 상태 업데이트 필요
		Member member = memberService.selectMember(getTokenUserEmail(request));
		int memberId = member.getMemberId();

		Pay pay = new Pay();
		pay.setWashId(washId);

		List<Orders> orders = ordersService.searchOrder(memberId, washId);
		if (orders.size() == 0) {
			pay.setPayState("2");
			payService.updatePay(pay);
			return "redirect:/orders";
		}

//		int total = 0;
//		
//		for (Orders order : orders) {
//			Laundry laundry = laundryService.getLaundry(order.getLaundryId());
//			total += order.getOrdersCount()*laundry.getLaundryPrice();
//		}
//		
//		if (member.getMemberSubscribe().equals("0")) {
//			for(OrdersDetails order : orders) {
//				order.setOrdersTotalPrice(order.getOrdersTotalPrice()+2500);
//			}
//		}
//		pay.setPayMoney(total);
//		payService.updatePay(pay);
		return "redirect:/orders/update/" + washId;
	}
}
