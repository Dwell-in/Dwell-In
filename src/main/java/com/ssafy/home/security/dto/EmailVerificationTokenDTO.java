package com.ssafy.home.security.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationTokenDTO {
	 private long id;
	 private String email;
	 private String token;
	 private LocalDateTime expiryDate;
	 private boolean isUsed;
}
