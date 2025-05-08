package com.ssafy.home.member.model.dto;

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
}
