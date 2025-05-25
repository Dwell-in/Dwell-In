package com.ssafy.home.member.model.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.StringRedisTemplate;
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
	private final StringRedisTemplate redisTemplate;
	
	@Override
	public int addMember(MemberDTO emp) {
		return dao.insertMember(emp);
	}

	@Override
	public MemberDTO findMemberDetail(String email) {
		return dao.selectMemberByEmail(email);
	}

	@Override
	public MemberDTO findMemberById(int id) {
		return dao.selectMemberById(id);
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
	public List<Map<String, ?>> findChatTargetList(int id) {
		List<Map<String, ?>> targets = new ArrayList<>();
		List<Integer> targetList = dao.selectChatTargetId(id);
		for (int targetId: targetList) {
			MemberDTO target = dao.selectMemberById(targetId);
			targets.add(Map.of("id",target.getId(),"name",target.getName(),"profileImg",target.getProfileImg()));
		}
		return targets;
	}
	
	@Override
	public String getRefreshToken(String email) {
	    try {
	        String redisToken = redisTemplate.opsForValue().get("refresh:" + email);
	        if (redisToken != null) {
	            return redisToken;
	        }
	        System.out.println("[INFO] Redis에 값 없음 → DB fallback");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        System.out.println("[WARN] Redis 연결 실패 → DB fallback");
	    }
	    return dao.selectRefreshTokenByEmail(email);
	}

	@Override
	public void modifyRefreshToken(String email, String refreshToken) {
	    try {
	        redisTemplate.opsForValue().set("refresh:" + email, refreshToken, Duration.ofDays(7));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	e.printStackTrace();
	        System.out.println("[WARN] Redis 저장 실패 → DB fallback");
	    }
	    dao.updateRefreshTokenByEmail(email, refreshToken);
	}

	@Override
	public void removeRefreshToken(String email) {
	    try {
	        redisTemplate.delete("refresh:" + email);
	    } catch (Exception e) {
	        System.out.println("[WARN] Redis 삭제 실패 → DB fallback");
	    }
	    dao.deleteRefreshTokenByEmail(email);
	}

	
}
