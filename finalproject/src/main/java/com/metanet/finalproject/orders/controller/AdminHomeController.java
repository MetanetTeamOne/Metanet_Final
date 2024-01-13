package com.metanet.finalproject.orders.controller;

import java.sql.Date;

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
		int payMoney = pay.getPayMoney();
		int totalPay = payService.sumPay(payMoney);
		// System.out.println("totalPay>>>>"+totalPay);

		String memHelpState = memhelp.getMemHelpState();
		int totalHelp = memHelpService.countMemHelp(memHelpState);
		
		int totalCount = ordersService.countOrder();
		System.out.println("totalCount============="+totalCount);
		
		model.addAttribute("memberCount", memberCount);
		model.addAttribute("totalPay", totalPay);
		model.addAttribute("totalHelp", totalHelp);
		model.addAttribute("totalCount", totalCount);
		return "admin/adminHome";
	}

}
