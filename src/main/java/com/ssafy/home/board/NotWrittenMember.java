package com.ssafy.home.board;

public class NotWrittenMember extends RuntimeException {
	public NotWrittenMember() {
        super("작성자가 아닙니다.");
    }
}
