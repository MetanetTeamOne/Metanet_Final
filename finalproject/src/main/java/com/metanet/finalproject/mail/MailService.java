package com.metanet.finalproject.mail;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;


@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    private static final String senderEmail= "kosatestmail@gmail.com";
    private static int number;

    public static void createNumber(){
        number = (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
    }

    public MimeMessage CreateMail(String mail){
        log.info("메일 생성중...");
        createNumber();
        log.info("인증번호 생성 완료: {}", number);
        MimeMessage message = javaMailSender.createMimeMessage();
        log.info("메시지 생성 완료: {}", message);

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        log.info("이메일 생성 완료: {}", message);
        return message;
    }

    public int sendMail(String mail){

        MimeMessage message = CreateMail(mail);
        log.info("메시지 다 만듬. 이제 보내기만 하면됨");
        javaMailSender.send(message);
        log.info("메시지 전송 완료!");
        return number;
    }
}