package com.metanet.finalproject.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.metanet.finalproject.member.service.MemberService;
import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.service.IOrdersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@Tag(name = "Orders", description = "주문 관리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-39-151-127.ap-northeast-2.compute.amazonaws.com:8888/",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*")
public class OrdersController {
	
//	@Value("${file.upload.directory}")
//	private String uploadDirectory;
	
	@Autowired
	IOrdersService ordersService;
	
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

	//	Header에서 Token으로 사용자 이메일 획득
	private String getTokenUserEmail(HttpServletRequest request) {
		log.info("이메일로 토큰 받는중...");
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

	@Operation(summary = "주문")
	@GetMapping("")
	public String getOrder(HttpServletRequest request, Model model) {
		int memberId = memberService.getMemberId(getTokenUserEmail(request));
		List<Orders> orders = ordersService.searchOrder(memberId);
		List<Laundry> laundrys = laundryService.getLaundry();
		model.addAttribute("orders", orders);
		model.addAttribute("laundrys", laundrys);
		return "member/orders_view";
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
	public String searchMonthOrder(HttpServletRequest request, Model model, @PathVariable int month){
		int memberId = memberService.getMemberId(getTokenUserEmail(request));
		List<Orders> orders = ordersService.searchMonthOrder(memberId, month);
		List<Laundry> laundrys = laundryService.getLaundry();
		model.addAttribute("orders", orders);
		model.addAttribute("laundrys", laundrys);
		return "member/orders_view :: memberTable";
	}
	
	@Operation(summary = "회원 개월별 주문 조회")
	@GetMapping("/{memberId}/{washId}")
	@ResponseBody
	public List<Orders> searchOrder(Model model, @PathVariable int memberId, @PathVariable int washId){
//		System.out.println(memberId + " : m w : " + washId);
//		log.info("memberId : {}, washId : {}", memberId, washId);
		return ordersService.searchOrder(memberId, washId);
	}

	@Operation(summary = "주문 입력 view")
	@GetMapping("/insert")
	public String insertOrder(Model model, HttpServletRequest request) {
		//try {
			Member member = memberService.selectMember(getTokenUserEmail(request));
		    //if (member == null) {
		    //    return "member/login";
		    //}
		    //else {
		    	int count = ordersService.countOrder(member.getMemberId());
		    	int washId = count + 1;
		    	System.out.println("count============"+count);
		    	Address addressList = addressService.getAddress(member.getMemberId());
				List<LaundryCategory> laundryCategoryList = laundryCategoryService.getLaundryCategory();
				System.out.println("laundryCategoryList>>>>"+laundryCategoryList);
				//System.out.println("laundryCategoryList 길이>>>"+laundryCategoryList.size());
				List<Laundry> laundryList = laundryService.getLaundryCategory(1);
				System.out.println("List<Laundry> laundryList>>>>>>>>>>>>>>>>" + laundryList);
				//model.addAttribute("ordersListSize", ordersListSize);
				model.addAttribute("washId", washId);
				model.addAttribute("member", member);
				model.addAttribute("addressList", addressList);
				model.addAttribute("laundryCategoryList", laundryCategoryList);
				model.addAttribute("laundryList", laundryList);
				return "member/orders_insert";
		    //}
		//} catch(Exception e){
			//return "member/login";
		//}  
			
	}

	@Operation(summary = "주문 입력")
	@PostMapping("/insert")
	public String insertOrder(Model model, Orders orders, HttpServletRequest request) {
		System.out.println("orders>>" + orders);
//		System.out.println("OrdersCount>>>>"+orders.getOrdersCount());
//		System.out.println("ordersPrice>>>>"+orders.getOrdersPrice());
		ordersService.insertOrder(orders);
		return "redirect:/orders/insertok";
	}
	
	@GetMapping("/count/{memberId}")
	@ResponseBody
	public int countOrder(@PathVariable int memberId) {
		return ordersService.countOrder(memberId);
	}
	
	/*@Operation(summary = "주문 입력")
	@PostMapping("/insert")
	public String insertOrder(Model model, Principal principal) throws IOException {
		System.out.println(orders);
		
		if (!file.isEmpty()) {
			orders.setOrdersImageData(file.getBytes());
			ordersService.insertOrder(orders);
		}
		else {
			orders.setOrdersImageData(null);
			ordersService.insertOrder(orders);
		}
		System.out.println("=============================");
		
		return "redirect:/orders/insertok";
	}*/
	
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
		System.out.println(">>>>>>>>>>>>>>"+orders);
		model.addAttribute("orders", orders);
		return "member/orders_update";
	}
	
	@Operation(summary = "주문 수정")
	@PostMapping("/update")
	public String updateOrder(Model model, Orders orders) {
//		System.out.println(orders);
		ordersService.updateOrder(orders);
		return "member/mypage_order";
	}
	
	@Operation(summary = "주문 삭제")
	@PostMapping("/delete/{ordersId}/{washId}")
	public String deleteOrder(Model model, @PathVariable("ordersId") int ordersId, @PathVariable("ordersId") int washId) {
		ordersService.deleteOrder(ordersId, washId);
		return "redirect:/orders";
	}
}
