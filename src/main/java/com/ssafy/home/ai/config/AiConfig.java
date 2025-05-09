package com.ssafy.home.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class AiConfig {
	
	@Value("${ssafy.ai.system-prompt}")
	String systemPrompt;
	
	@Bean
	ChatClient simpleChatClient(ChatClient.Builder builder) {
		return builder.defaultSystem(systemPrompt).defaultAdvisors(new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE-1)).build();
	}
	
}
