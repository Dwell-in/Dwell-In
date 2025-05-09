package com.ssafy.home.board.model.dto;


import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	private int boardId;
	private int categoryId;
	private String title;
	private String content;
	private int userId;
	private int viewCount;
	private int like;
	private int dislike;
	private LocalDateTime regDate;

}
