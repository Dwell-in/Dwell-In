package com.ssafy.home.house.model.service;

import java.util.List;

import com.ssafy.home.house.model.dto.HouseSearchCondition;
import com.ssafy.home.house.model.dto.CompareSeqNmRequestDTO;
import com.ssafy.home.house.model.dto.HouseinfoDTO;
import com.ssafy.home.house.model.dto.HouserdealDTO;
import com.ssafy.home.search.model.dto.DongDTO;

public interface HouseService {
	HouseinfoDTO findInfo(String aptSeq);
	List<HouseinfoDTO> findInfoList(DongDTO dongDTO);
	List<HouseinfoDTO> findInBounds(double swLat, double swLng, double neLat, double neLng);
	int modifyViewCount(String aptSeq);
	int findViewCount(String aptSeq);
	List<HouseinfoDTO> findInfoListByCondition(HouseSearchCondition condition);
	List<HouseinfoDTO> findInfoList(List<CompareSeqNmRequestDTO> aptList);
}
