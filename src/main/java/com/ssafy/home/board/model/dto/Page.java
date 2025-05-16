package com.ssafy.home.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Page {
    private int page;         // 현재 페이지 번호
    private int size;         // 페이지당 항목 수
    private int totalCount;   // 총 데이터 개수
    private int totalPages;   // 전체 페이지 수
    private int offset;       // OFFSET 값

    public void calculate(int totalCount) {
        this.totalCount = totalCount;
        this.totalPages = (int) Math.ceil((double) totalCount / size);
        this.offset = (page - 1) * size;
    }
}

