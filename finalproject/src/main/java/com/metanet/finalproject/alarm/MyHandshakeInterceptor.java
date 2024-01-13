package com.metanet.finalproject.alarm;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.util.WebUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class MyHandshakeInterceptor implements HandshakeInterceptor {

	 @Override
	    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response
	              , WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
	        if (request instanceof ServletServerHttpRequest) {
	            ServletServerHttpRequest servletServerRequest = (ServletServerHttpRequest) request;
	            HttpServletRequest servletRequest = servletServerRequest.getServletRequest();
	            //log.info("servletRequest="+servletRequest);
	            // 쿠키 정보 전달
	            if (servletRequest!=null) {
	            	Cookie token = WebUtils.getCookie(servletRequest, "token");
		            attributes.put("token", token.getValue());
		            //log.info("token="+token.getValue());
	            }
	            
	        }
	        return true;
	    }

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
		// TODO Auto-generated method stub

	}

}
