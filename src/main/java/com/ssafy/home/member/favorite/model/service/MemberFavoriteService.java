package com.ssafy.home.member.favorite.model.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.home.member.favorite.model.dao.MemberFavoriteDAO;
import com.ssafy.home.member.favorite.model.dto.MemberFavoriteDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFavoriteService {
	private final MemberFavoriteDAO memberFavoriteDao;
	
	
	public void addFavorite(MemberFavoriteDTO dto) {
		
		memberFavoriteDao.insertMemberFavorite(dto);
	}

	public MemberFavoriteDTO getMemberFavorite(long memberId){
		return memberFavoriteDao.selectMemberFavorite(memberId);
	}

}
