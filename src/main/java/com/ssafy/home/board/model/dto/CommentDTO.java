package com.ssafy.home.board.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO {
	private int commentId;
	private int boardId;
	private int userId;
	private String userName;
	private String content;
	private int like;
	private int dislike;
	private String regdate;
}
