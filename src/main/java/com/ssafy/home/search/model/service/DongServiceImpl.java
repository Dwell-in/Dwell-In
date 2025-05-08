package com.ssafy.home.search.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.home.house.model.dao.HouseinfoDAO;
import com.ssafy.home.house.model.dto.HouseinfoDTO;
import com.ssafy.home.search.model.dao.DongDAO;
import com.ssafy.home.search.model.dto.DongDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DongServiceImpl implements DongService {
	private final DongDAO dongDao;

	@Override
	public List<String> findSidoList() {
		return dongDao.selectSidoList();
	}

	@Override
	public List<String> findGugunList(String sido) {
		return dongDao.selectGugunList(sido);
	}

	@Override
	public List<String> findDongList(String sido, String gugun) {
		return dongDao.selectDongList(sido, gugun);
	}

}
