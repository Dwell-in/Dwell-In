package com.ssafy.home.websocket.model.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.websocket.model.dao.ChatMessageDAO;
import com.ssafy.home.websocket.model.dao.ChatReadDAO;
import com.ssafy.home.websocket.model.dao.ChatRoomDAO;
import com.ssafy.home.websocket.model.dto.ChatMessageDTO;
import com.ssafy.home.websocket.model.dto.ChatReadDTO;
import com.ssafy.home.websocket.model.dto.ChatRoomDTO;
import com.ssafy.home.websocket.model.dto.UnreadCountDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

	private final ChatRoomDAO chatRoomDAO;
	private final ChatMessageDAO chatMessageDAO;
	private final ChatReadDAO chatReadDAO;

	@Transactional
	@Override
	public String findRoomId(ChatRoomDTO chatRoom) {
	    String roomId = chatRoomDAO.selectRoomId(chatRoom);
	    if (roomId == null) {
	        roomId = UUID.randomUUID().toString();
	        chatRoom.setRoomId(roomId);
	        chatRoomDAO.insertChatRoom(chatRoom);

	        // 채팅방 생성 시 읽음 정보 초기화
	        chatReadDAO.insertInitialRead(chatRoom.getUser1Id(), roomId);
	        chatReadDAO.insertInitialRead(chatRoom.getUser2Id(), roomId);
	    }
	    return roomId;
	}

	@Override
	public List<ChatMessageDTO> findChatList(String roomId) {
		return chatMessageDAO.selectChatMessageList(roomId);
	}

	@Override
	public long addChatMessage(ChatMessageDTO chatMessage) {
		chatMessageDAO.insertChatMessage(chatMessage);
		return chatMessage.getMessageId(); 
	}

	@Override
	public List<Integer> getUserIdsByRoomId(String roomId) {
		return chatRoomDAO.selectUserIdsByRoomId(roomId);
	}

	@Override
	public ChatReadDTO findReadInfo(int userId, String roomId) {
		return chatReadDAO.selectByUserIdAndRoomId(userId, roomId);
	}
	
	@Override
	public void updateReadInfo(int userId, String roomId) {
		updateReadInfo(userId, roomId, 0L);
	}
	
	@Override
	public void updateReadInfo(int userId, String roomId, Long lastMessageId) {
		ChatReadDTO dto = new ChatReadDTO();
		dto.setUserId(userId);
		dto.setRoomId(roomId);
		dto.setLastReadMessageId(lastMessageId);
		chatReadDAO.insertOrUpdateRead(dto);
	}

	@Override
	public List<UnreadCountDTO> countUnreadMessages(int userId) {
		return chatReadDAO.countUnreadMessages(userId);
	}

}
