package com.metanet.finalproject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
@Slf4j
public class ErrorPageController implements ErrorController {

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response) {
        log.info("handleError 실행");
        int status = response.getStatus();
        log.info("status: {}", status);

        if(status == 400){ // 잘못된 요청
            return "/error/"+ status;
        } else if (status == 401) { //권한 없음
            return "/error/"+ status;
        } else if (status == 403) { //금지됨
            return "/error/"+ status;
        } else if (status == 404) { //찾을 수 없음
            return "/error/" + status;
        } else {
            return "/error/common";
        }
    }
}
