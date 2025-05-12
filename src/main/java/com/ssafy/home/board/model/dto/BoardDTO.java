package com.ssafy.home.board.model.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
	private int boardId;
	private int categoryId;
	private String categoryName;
	private String title;
	private String content;
	private int userId;
	private String userName;
	private int viewCount;
	private int like;
	private int dislike;
	private LocalDateTime regTime;

}
