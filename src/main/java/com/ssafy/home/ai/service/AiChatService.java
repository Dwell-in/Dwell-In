package com.ssafy.home.ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiChatService {
	
	private final ChatClient simpleChatClient;

	public Object simpleGenration(String userInput) {
		//System.out.println(userInput);
		var responseSpec = simpleChatClient.prompt()
				.system(spec -> spec.param("language", "korean").param("character", "expert"))
				.user(userInput).call();
		return responseSpec.content();
	}

}
