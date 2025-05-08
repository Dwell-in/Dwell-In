package com.ssafy.home.member.model.service;

import org.springframework.stereotype.Service;

import com.ssafy.home.member.model.dao.MemberDAO;
import com.ssafy.home.member.model.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {
	
	private final MemberDAO dao;
	
	@Override
	public int addMember(MemberDTO emp) {
		return dao.insertMember(emp);
	}

	@Override
	public MemberDTO findMemberDetail(String email, String password) {
		return dao.selectMember(email, password);
	}

	@Override
	public MemberDTO findMemberDetail(String email) {
		return dao.selectMemberByEmail(email);
	}

	@Override
	public int modifyMember(MemberDTO member) {
		return dao.updateMember(member);
	}

	@Override
	public int removeMember(String email, String password) {
		return dao.deleteMember(email, password);
	}

	@Override
	public int removeMember(String email) {
		return dao.deleteMemberByEmail(email);
	}
	
}
