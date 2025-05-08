package com.ssafy.home.board.model.dto;

public class SearchCondition {
    // 검색에 사용할 조건
    private String key;
    private String word;
    private int currentPage;
    private int itemsPerPage = 5;

    public SearchCondition() {
    }

    public SearchCondition(String key, String word, int currentPage) {
        this.key = key;
        this.word = word;
        this.currentPage = currentPage;

    }

    /**
     * 페이징을 위해 현재 페이지의 offset 반환
     * 
     * @return
     */
    public int getOffset() {
        return (currentPage - 1) * itemsPerPage;
    }

    /**
     * SearchCondition에 key와 word가 모두 설정되어있는지 확인
     * 
     * @param condition
     * @return
     */
    public boolean hasKeyword() {
        return key != null && !key.isBlank() && word != null && !word.isBlank();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    @Override
    public String toString() {
        return "SearchCondition [key=" + key + ", word=" + word + ", currentPage=" + currentPage + ", itemsPerPage=" + itemsPerPage + "]";
    }

}
