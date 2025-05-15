package com.ssafy.home.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer {
	private static final String DEVELOP_FRONT_ADDRESS1 = "http://localhost:8080";
	private static final String DEVELOP_FRONT_ADDRESS2 = "https://dwell-in.github.io";

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setAllowedOrigins(DEVELOP_FRONT_ADDRESS1, DEVELOP_FRONT_ADDRESS2).withSockJS();
	}
	
	@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 구독(sub) : 접두사로 시작하는 메시지를 브로커가 처리하도록 설정합니다. 클라이언트는 이 접두사로 시작하는 주제를 구독하여 메시지를 받을 수 있습니다.
        registry.enableSimpleBroker("/sub");

        // 발행(pub) : 접두사로 시작하는 메시지는 @MessageMapping이 달린 메서드로 라우팅됩니다. 클라이언트가 서버로 메시지를 보낼 때 이 접두사를 사용합니다.
        registry.setApplicationDestinationPrefixes("/pub");
    }
}
