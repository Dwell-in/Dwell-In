package com.ssafy.home.ai.tool;

import java.time.LocalDateTime;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DateTimeTools {
	@Tool(description = "현재 사용자의 시스템 시간을 가져와서 반환한다.")
	public LocalDateTime getCurrentCateTime() {
		return LocalDateTime.now();
	}
}
