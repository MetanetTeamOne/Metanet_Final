package com.metanet.finalproject.reply.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.metanet.finalproject.memhelp.controller.AdminMemhelpController;
import com.metanet.finalproject.memhelp.model.Memhelp;
import com.metanet.finalproject.memhelp.service.IMemhelpService;
import com.metanet.finalproject.reply.dto.InsertReplyDto;
import com.metanet.finalproject.reply.model.Reply;
import com.metanet.finalproject.reply.service.IReplyService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@Tag(name = "AdminReply", description = "관리자 - 문의사항 답변 관리 API")
@CrossOrigin(origins = {"http://localhost:8085", 
		"http://ec2-3-39-151-127.ap-northeast-2.compute.amazonaws.com:8888/",
		"http://metawash.kro.kr:8888/"}, allowedHeaders = "*", allowCredentials = "true")
public class AdminReplyController {
	@Autowired
	IReplyService replyService;
	
	@Autowired
	IMemhelpService memhelpService;
	
	@Operation(summary = "관리자 답변 등록 view")
	@GetMapping("/admin/reply/insert/{memHelpNum}")
	public String insertReplyOfMemhelp(@PathVariable("memHelpNum") int memHelpNum, Model model) {
		
		System.out.println("!!!!!!!!!!!");
		Memhelp memhelp = memhelpService.searchMemhelpByMemhelpIdOnly(memHelpNum);
		
		System.out.println("쓴이 : " + memhelp.getMemberId() + " 제목 : " + memhelp.getMemHelpTitle() + " 내용 : " + memhelp.getMemHelpContent());;
		
		model.addAttribute("memberId", memhelp.getMemberId());
		model.addAttribute("memHelpTitle", memhelp.getMemHelpTitle());
		model.addAttribute("memHelpContent", memhelp.getMemHelpContent());
		
		
		InsertReplyDto replyDto = new InsertReplyDto();
		
		replyDto.setMemHelpNum(memHelpNum);
		
		System.out.println("문의 등록 번호는 : " + memHelpNum);
		
		model.addAttribute("replyDto", replyDto);
		
		return "admin/adminReply";
	}
	
	@Operation(summary = "관리자 답변 등록")
	@PostMapping("/admin/reply/insert")
	public String insertReplyOfMemhelp(@ModelAttribute InsertReplyDto replyDto) {
		Reply reply = new Reply();
		
		System.out.println("관리자 답변 등록 번호 : " + replyDto.getMemHelpNum()+
						   "관리자 답변 등록 내용 : " + replyDto.getRepContent());
		
		reply.setMemHelpNum(replyDto.getMemHelpNum());
		reply.setRepContent(replyDto.getRepContent());
		
		// reply 멤버 필드인 id와 date는 시퀀스와 sysdate 처리.
		
		// 해당 문의사항 번호에 맞는 답변을 등록한다.
		replyService.insertReplyToMemhelp(reply);
		
		// 문의사항 번호에 맞는 문의사항에 대해서 상태 업데이트를 한다. 답변 대기 -> 답변 완료
		memhelpService.updateStateOfMemhelp(replyDto.getMemHelpNum());
		
		return "redirect:/admin/memhelp";
	}
}
