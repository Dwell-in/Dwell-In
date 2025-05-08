package com.ssafy.home.house.model.service;

import java.util.List;

import com.ssafy.home.house.model.dto.HouseinfoDTO;
import com.ssafy.home.search.model.dto.DongDTO;

public interface HouseService {
	List<HouseinfoDTO> findInfoList(DongDTO dongDTO);
	int modifyViewCount(String aptSeq);
	int findViewCount(String aptSeq);
}
