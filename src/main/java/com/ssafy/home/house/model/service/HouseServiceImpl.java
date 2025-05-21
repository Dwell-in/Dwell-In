package com.ssafy.home.house.model.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.house.model.dao.HouseinfoDAO;
import com.ssafy.home.house.model.dto.CompareSeqNmRequestDTO;
import com.ssafy.home.house.model.dto.HouseinfoDTO;
import com.ssafy.home.house.model.dto.HouserdealDTO;
import com.ssafy.home.search.model.dao.DongDAO;
import com.ssafy.home.search.model.dto.DongDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {
	
	private final DongDAO dongDao;
	private final HouseinfoDAO houseinfoDAO;
	

	@Override
	public HouseinfoDTO findInfo(String aptSeq) {
		return houseinfoDAO.selectInfo(aptSeq);
	}
	
	@Override
	public List<HouseinfoDTO> findInfoList(DongDTO dongDTO) {
		String code = dongDao.selectCode(dongDTO);
		String sggCd = code.substring(0, 5);
		String umdCd = code.substring(5);
		List<HouseinfoDTO> list = houseinfoDAO.selectInfoList(sggCd, umdCd);
		return list;
	}
	
    @Override
    public List<HouseinfoDTO> findInBounds(double swLat, double swLng, double neLat, double neLng) {
        return houseinfoDAO.selectInBounds(swLat, swLng, neLat, neLng);
    }
	
	@Override
	@Transactional
	public int modifyViewCount(String aptSeq) {
		houseinfoDAO.updateViewCount(aptSeq);
		return houseinfoDAO.selectViewCount(aptSeq);
	}

	@Override
	public int findViewCount(String aptSeq) {
		return houseinfoDAO.selectViewCount(aptSeq);
	}

	@Override
	public List<HouseinfoDTO> findInfoList(List<CompareSeqNmRequestDTO> aptList) {
		// TODO Auto-generated method stub
		return aptList.stream()
                .map(dto -> houseinfoDAO.selectInfo(dto.getAptSeq()))
                .filter(Objects::nonNull) // 혹시 null 방지
                .collect(Collectors.toList());
	}



}
