package com.ssafy.home.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.home.member.model.dao.MemberDAO;
import com.ssafy.home.member.model.dto.MemberDTO;
import com.ssafy.home.security.dto.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
 public class CustomUserDetailsService implements UserDetailsService {
	private final MemberDAO mDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MemberDTO member = mDao.selectMemberByEmail(username);
		if(member == null) {
			throw new UsernameNotFoundException(username);
		}else {
			return new CustomUserDetails(member);
		}
		
	}
}
