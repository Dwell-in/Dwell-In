package com.ssafy.home.websocket.model.service;

import java.util.List;

import com.ssafy.home.websocket.model.dto.ChatMessageDTO;
import com.ssafy.home.websocket.model.dto.ChatReadDTO;
import com.ssafy.home.websocket.model.dto.ChatRoomDTO;
import com.ssafy.home.websocket.model.dto.UnreadCountDTO;

public interface ChatService {
	String findRoomId(ChatRoomDTO chatRoom);
	List<ChatMessageDTO> findChatList(String roomId);
	long addChatMessage(ChatMessageDTO chatMessage);
	List<Integer> getUserIdsByRoomId(String roomId);
	ChatReadDTO findReadInfo(int userId, String roomId);
	void updateReadInfo(int userId, String roomId);
	void updateReadInfo(int userId, String roomId, Long lastMessageId);
	List<UnreadCountDTO> countUnreadMessages(int userId);
}
