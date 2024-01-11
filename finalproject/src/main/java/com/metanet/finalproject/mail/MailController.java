package com.metanet.finalproject.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final MailService mailService;

//    @GetMapping("/")
//    public String MailPage(){
//        return "Main";
//    }

    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(String mail){

        log.info("메일 보내는중... {}", mail);
        int number = mailService.sendMail(mail);
        log.info("인증번호 반환 완료: {}", number);

        String num = "" + number;

//        log.info("인증번호: {}", num);
        return num;
    }

}