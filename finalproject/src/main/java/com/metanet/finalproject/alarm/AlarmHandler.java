package com.metanet.finalproject.alarm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.metanet.finalproject.jwt.JwtTokenProvider;

import lombok.extern.log4j.Log4j2;


@Configuration
@EnableWebSocket
@Log4j2
public class AlarmHandler extends TextWebSocketHandler {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	// 로그인 인원의 전체 세션
	private Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();
	
	private List<WebSocketSession> sessions = new ArrayList<>();
	
	//클라이언트 웹 소켓 생성
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("Socket 연결");
		sessions.add(session);
		log.info("username:"+currentUserName(session));//현재 접속한 사람
		String senderId = currentUserName(session);
		userSessionsMap.put(senderId,session);	}
	
	// 클라이언트 메시지
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("ssesion="+currentUserName(session));
		String[] payload =  message.getPayload().toString().split(",");
		String cmd = payload[0];
		String receiver = payload[1];
		String msgTitle = payload[2];
		String msgContent = payload[3];
		
		WebSocketSession receiverSession = userSessionsMap.get(receiver);
		
		//구독 만기
		if("sub".equals(cmd) && receiverSession !=null ) {
			TextMessage sendMsg = new TextMessage(msgTitle+","+msgContent);
			receiverSession.sendMessage(sendMsg);
		}
			
		//문의 답변 등록
		
		// 진행 상황 
		
	}
	// 클라이언트 웹 소켓 생성 연결 종료
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("Socket 연결 종료");
		sessions.remove(session);
		userSessionsMap.remove(currentUserName(session),session);
	}
	
	// 클라이언트 웹 소켓 생성 에러
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.info(session.getId() + " 익셉션 발생: " + exception.getMessage());
	}
	
	// userEmail 반환 
	private String currentUserName(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		String token = httpSession.get("token").toString();
		return jwtTokenProvider.getUserId(token);
	}
	
}

