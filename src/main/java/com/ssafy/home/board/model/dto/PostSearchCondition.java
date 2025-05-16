package com.ssafy.home.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchCondition {
    private Long categoryId;
    private String userName;
    private String title;
    private String content;
    
    // 페이징 관련
    private int page = 1;  // 기본값: 1페이지
    private int size = 10; // 기본값: 페이지당 10개

    public int getOffset() {
        return (page - 1) * size;
    }
}

