package com.ssafy.home.websocket.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.websocket.model.dto.ChatRoomDTO;

@Mapper
public interface ChatRoomDAO {
	String selectRoomId(ChatRoomDTO chatRoom);
	int insertChatRoom(ChatRoomDTO chatRoom);
	List<Integer> selectUserIdsByRoomId(String roomId);
}
