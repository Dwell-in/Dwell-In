package com.ssafy.home.starred.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.starred.model.dao.StarredDao;
import com.ssafy.home.starred.model.dto.StarredDTO;
import com.ssafy.home.starred.model.dto.StarredHouseInfoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StarredService {
	
	private final StarredDao sdao;

	public boolean isStarred(StarredDTO starred) {
		// TODO Auto-generated method stub
		return sdao.isStarred(starred);
	}
	
	public List<StarredHouseInfoDTO> findStarred(StarredDTO starred){
		return sdao.selectStarred(starred);
	}
	
	
	@Transactional
	public void addStarred(StarredDTO starred) {
		// TODO Auto-generated method stub
		sdao.insertStarred(starred);
	}
	
	@Transactional
	public void removeStarred(StarredDTO starred) {
		sdao.deleteStarred(starred);
	}

}
