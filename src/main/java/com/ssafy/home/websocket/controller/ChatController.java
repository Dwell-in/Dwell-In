package com.ssafy.home.websocket.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.websocket.model.dto.ChatMessageDTO;
import com.ssafy.home.websocket.model.dto.ChatRoomDTO;
import com.ssafy.home.websocket.model.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController implements RestControllerHelper{
	private final SimpMessageSendingOperations smso;
	private final ChatService chatService;
	
	// 채팅방 ID 반환
    @ResponseBody
    @GetMapping("/chat/roomId")
    public ResponseEntity<?> getChatMessages(@ModelAttribute ChatRoomDTO chatRoomDTO){
        String roomId = chatService.findRoomId(chatRoomDTO);
        return handleSuccess(roomId);
    }
	
	// 채팅 리스트 반환
    @ResponseBody
    @GetMapping("/chat/{id}")
    public ResponseEntity<?> getChatMessages(@PathVariable String id){
        List<ChatMessageDTO> list = chatService.findChatList(id);
        return handleSuccess(list);
    }

    //메시지 송신 및 수신, 클라이언트 단에선 /pub/message로 요청
    @MessageMapping("/message")
    public void receiveMessage(ChatMessageDTO chat) {
    	chatService.addChatMessage(chat);
        // 메시지를 해당 채팅방 구독자들에게 전송
        smso.convertAndSend("/sub/chatroom/" + chat.getRoomId(), chat);
    }
}
