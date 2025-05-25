package com.ssafy.home.websocket.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.home.common.RestControllerHelper;
import com.ssafy.home.member.model.service.MemberService;
import com.ssafy.home.websocket.model.dto.ChatMessageDTO;
import com.ssafy.home.websocket.model.dto.ChatReadDTO;
import com.ssafy.home.websocket.model.dto.ChatRoomDTO;
import com.ssafy.home.websocket.model.dto.UnreadCountDTO;
import com.ssafy.home.websocket.model.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController implements RestControllerHelper {
	private final SimpMessageSendingOperations smso;
	private final ChatService chatService;
	private final MemberService memberService;

	// 채팅 상대 리스트
	@ResponseBody
	@GetMapping("/chat/targets/{userId}")
	public ResponseEntity<?> getChatTargets(@PathVariable int userId) {
		return handleSuccess(memberService.findChatTargetList(userId));
	}

	// 채팅방 ID 반환
	@ResponseBody
	@GetMapping("/chat/roomId")
	public ResponseEntity<?> getChatMessages(@ModelAttribute ChatRoomDTO chatRoomDTO) {
		String roomId = chatService.findRoomId(chatRoomDTO);
		return handleSuccess(roomId);
	}

	// 채팅 리스트 반환
	@ResponseBody
	@GetMapping("/chat/{id}")
	public ResponseEntity<?> getChatMessages(@PathVariable String id) {
		List<ChatMessageDTO> list = chatService.findChatList(id);
		return handleSuccess(list);
	}

	// 메시지 송신 및 수신, 클라이언트 단에선 /pub/message로 요청
	@MessageMapping("/message")
	public void receiveMessage(ChatMessageDTO chat) {
		chatService.addChatMessage(chat);
		// 메시지를 해당 채팅방 구독자들에게 전송
		smso.convertAndSend("/sub/chatroom/" + chat.getRoomId(), chat);

		// 채팅방 참여자 조회
		List<Integer> userIds = chatService.getUserIdsByRoomId(chat.getRoomId());

		// 상대방에게 메세지 알림
		for (int userId : userIds) {
			if (userId != Integer.parseInt(chat.getSender())) {
				smso.convertAndSend("/sub/unread/" + userId, "new");
			}
		}
	}

	@GetMapping("/chat/unread-count/{userId}")
	public ResponseEntity<?> getUnreadMessageCount(@PathVariable int userId) {
		List<UnreadCountDTO> list = chatService.countUnreadMessages(userId);
	    Map<String, Integer> result = new HashMap<>();
	    int total = 0;
	    for (UnreadCountDTO dto : list) {
	        result.put(dto.getRoomId(), dto.getUnreadCount());
	        total += dto.getUnreadCount();
	    }
	    result.put("total", total);
		return handleSuccess(result);
	}

	@PostMapping("/chat/read")
	public ResponseEntity<?> updateReadInfo(@RequestBody ChatReadDTO dto) {
		chatService.updateReadInfo(dto.getUserId(), dto.getRoomId(), dto.getLastReadMessageId());
		return handleSuccess(dto.getRoomId());
	}
	
	@GetMapping("/chat/read-info")
	public ResponseEntity<?> getReadInfo(@RequestParam int userId, @RequestParam String roomId) {
	    ChatReadDTO dto = chatService.findReadInfo(userId, roomId);
	    return handleSuccess(dto);
	}
}
