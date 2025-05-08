package com.ssafy.home.search.model.service;

import java.util.List;

public interface DongService {
	List<String> findSidoList();
	List<String> findGugunList(String sido);
	List<String> findDongList(String sido, String gugun);
}
