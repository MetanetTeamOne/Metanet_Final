package com.metanet.finalproject.alarm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Autowired
    AlarmHandler alarmHandler;   

	@Autowired
	MyHandshakeInterceptor myHandshakeInterceptor;
	
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(alarmHandler, "/ws/alarm")
        		.addInterceptors(myHandshakeInterceptor)
                .setAllowedOrigins("*");
    }   
    
}


