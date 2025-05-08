package com.ssafy.home.websocket.model.service;

import java.util.List;

import com.ssafy.home.websocket.model.dto.ChatMessageDTO;
import com.ssafy.home.websocket.model.dto.ChatRoomDTO;

public interface ChatService {
	String findRoomId(ChatRoomDTO chatRoom);
	List<ChatMessageDTO> findChatList(String roomId);
	int addChatMessage(ChatMessageDTO chatMessage);
}
