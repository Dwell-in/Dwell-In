package com.ssafy.home.websocket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
	private String roomId;
	private int user1Id;
	private int user2Id;
}
