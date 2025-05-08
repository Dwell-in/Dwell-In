package com.ssafy.home.member.model.dto;

import java.util.Base64;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	private String email;		// 이메일
	private String name;		// 이름
	private String password;	// 비밀번호
	private String phone;		// 휴대폰 번호
	private String role;		// role
	private byte[] profile;
	private String kakaoId;
	
	public String getProfileImg() {
		if(profile!=null) return "data:image/jpeg;base64,"+Base64.getEncoder().encodeToString(profile);
		else return "/resources/img/default_profile.png";
	}
}
