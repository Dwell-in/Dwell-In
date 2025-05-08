package com.ssafy.home.websocket.model.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.websocket.model.dao.ChatMessageDAO;
import com.ssafy.home.websocket.model.dao.ChatRoomDAO;
import com.ssafy.home.websocket.model.dto.ChatMessageDTO;
import com.ssafy.home.websocket.model.dto.ChatRoomDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatRoomDAO chatRoomDAO;
	private final ChatMessageDAO chatMessageDAO;
	
	@Transactional
	@Override
	public String findRoomId(ChatRoomDTO chatRoom) {
		String roomId = chatRoomDAO.selectRoomId(chatRoom);
		if (roomId == null) {
			roomId = UUID.randomUUID().toString();
			chatRoom.setRoomId(roomId);
			chatRoomDAO.insertChatRoom(chatRoom);
		}
		
		return roomId;
	}
	
	@Override
	public List<ChatMessageDTO> findChatList(String roomId) {
		return chatMessageDAO.selectChatMessageList(roomId);
	}

	@Override
	public int addChatMessage(ChatMessageDTO chatMessage) {
		return chatMessageDAO.insertChatMessage(chatMessage);
	}

}
