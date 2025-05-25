package com.ssafy.home.websocket.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.home.websocket.model.dto.ChatReadDTO;
import com.ssafy.home.websocket.model.dto.UnreadCountDTO;

@Mapper
public interface ChatReadDAO {
    ChatReadDTO selectByUserIdAndRoomId(@Param("userId") int userId, @Param("roomId") String roomId);

    void insertOrUpdateRead(ChatReadDTO dto);

    void updateLastReadMessageId(@Param("userId") int userId, @Param("roomId") String roomId, @Param("messageId") Long messageId);

    List<UnreadCountDTO> countUnreadMessages(int userId);
    
    void insertInitialRead(@Param("userId") int userId, @Param("roomId") String roomId);
}
