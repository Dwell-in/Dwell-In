package com.ssafy.home.websocket.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.websocket.model.dto.ChatMessageDTO;

@Mapper
public interface ChatMessageDAO {
	List<ChatMessageDTO> selectChatMessageList(String roomId);
	int insertChatMessage(ChatMessageDTO chatMessageDTO);
}
