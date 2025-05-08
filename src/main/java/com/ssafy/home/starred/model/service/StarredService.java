package com.ssafy.home.starred.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.home.starred.model.dao.StarredDao;
import com.ssafy.home.starred.model.dto.StarredDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StarredService {
	
	private final StarredDao sdao;

	public boolean isStarred(StarredDTO starred) {
		// TODO Auto-generated method stub
		return sdao.isStarred(starred);
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
