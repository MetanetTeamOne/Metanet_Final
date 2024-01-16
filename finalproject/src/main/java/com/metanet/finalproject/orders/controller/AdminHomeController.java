package com.metanet.finalproject.orders.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.metanet.finalproject.member.model.Member;
import com.metanet.finalproject.member.service.IMemberService;
import com.metanet.finalproject.memhelp.model.Memhelp;
import com.metanet.finalproject.memhelp.service.IMemhelpService;
import com.metanet.finalproject.orders.model.Orders;
import com.metanet.finalproject.orders.service.IOrdersService;
import com.metanet.finalproject.pay.model.Pay;
import com.metanet.finalproject.pay.service.IPayService;

@Controller
public class AdminHomeController {

	@Autowired
	IMemberService memberService;

	@Autowired
	IPayService payService;

	@Autowired
	IMemhelpService memHelpService;

	@Autowired
	IOrdersService ordersService;
	

	@GetMapping("/admin")
	public String adminHome(Model model, @ModelAttribute("member") Member member, @ModelAttribute("pay") Pay pay,
			@ModelAttribute("memhelp") Memhelp memhelp, @ModelAttribute("orders") Orders orders) {
		String memberJoinState = member.getMemberJoinState();
		int memberCount = memberService.getMemberCount(memberJoinState);
		// System.out.println("memberCount >>>> "+memberCount);
		//int payMoney = pay.getPayMoney();
		int totalPay = payService.sumPay();
		//System.out.println("totalPay>>>>>"+totalPay);
		
		//System.out.println("pay.getPayId()>>"+pay.getPayId());
		//System.out.println("list>>>>>>>>>>>>>>"+payService.getPay());
		List<Pay> payList = payService.getPay();
		List<Date> payDateList = new ArrayList<>();
		List<Integer> payMoneyList = new ArrayList<>();
		
		for (Pay pay2 : payList) {
		    Date payDate = pay2.getPayDate();
		    payDateList.add(payDate);
		}
		
		for (Pay pay2 : payList) {
		    int payMoney = pay2.getPayMoney();
		    payMoneyList.add(payMoney);
		}
		
		//System.out.println("payDateList==========="+payDateList);
		//System.out.println("payMoneyList====="+payMoneyList);
		
		String memHelpState = memhelp.getMemHelpState();
		int totalHelp = memHelpService.countMemHelp(memHelpState);
		
		int totalCount = ordersService.countOrder();
		//System.out.println("totalCount============="+totalCount);
		
		List<Orders> ordersAllList = ordersService.searchOrdersList();
		
		model.addAttribute("memberCount", memberCount);
		model.addAttribute("totalPay", totalPay);
		model.addAttribute("totalHelp", totalHelp);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("ordersAllList", ordersAllList);
		model.addAttribute("memhelpList", memHelpService.searchAllMemhelp());
		model.addAttribute("payDateList", payDateList);
		model.addAttribute("payMoneyList", payMoneyList);
		return "admin/adminHome";
	}

}
