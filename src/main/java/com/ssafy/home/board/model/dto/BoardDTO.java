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
	private String title;
	private String content;
	private String email;
	private int viewCount;
	private LocalDateTime regDate;
	
	

	public BoardDTO(String email, LocalDateTime regDate, String title, int viewCount) {
		this.email = email;
		this.regDate = regDate;
		this.title = title;
		this.viewCount = viewCount;
	}


	public BoardDTO(String title, String content, String email, int viewCount, LocalDateTime regDate) {
		this(0, title, content, email, viewCount, regDate);
	}


	
	
}
