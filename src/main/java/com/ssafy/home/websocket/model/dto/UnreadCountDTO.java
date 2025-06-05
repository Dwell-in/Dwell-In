package com.ssafy.home.websocket.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnreadCountDTO {
    private String roomId;
    private int unreadCount;
}
