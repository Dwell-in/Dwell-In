package com.ssafy.home.member.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	@Override
	public String findEmailByKakaoId(String id) {
		return dao.selectEmailByKakaoId(id);
	}

	@Override
	public List<Map<String, ?>> findChatTargetList(String loginUserEmail) {
		List<Map<String, ?>> targets = new ArrayList<>();
		List<String> targetEmails = dao.selectChatTargetEmail(loginUserEmail);
		for (String email : targetEmails) {
			MemberDTO target = dao.selectMemberByEmail(email);
			targets.add(Map.of("email",target.getEmail(),"name",target.getName(),"profileImg",target.getProfileImg()));
		}
		return targets;
	}
	
}
