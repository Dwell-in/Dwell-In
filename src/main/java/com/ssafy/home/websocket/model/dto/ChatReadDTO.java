package com.ssafy.home.websocket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatReadDTO {
    private int id;
    private int userId;
    private String roomId;
    private Long lastReadMessageId;
}
