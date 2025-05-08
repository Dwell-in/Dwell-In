package com.ssafy.home.websocket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
	private long messageId;
	private String roomId;
	private String sender;
	private String content;
	private String sentAt;
}
