package com.ssafy.home.security.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {

	private final JavaMailSender mailSender;

	public void sendVerificationEmail(String toEmail, String token) {
		try {

			String subject = "[Dwell-In] 이메일 인증 요청";
			String frontUrl = "http://localhost:8080";
			String verificationUrl = frontUrl + "/email/verify?token=" + token;

			String htmlContent = """
					    <p>다음 링크를 클릭하여 이메일 인증을 완료해주세요:</p>
					    <p><a href="%s">%s</a></p>
					""".formatted(verificationUrl, verificationUrl);
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

			helper.setTo(toEmail);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);

			mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendTempPassword(String toEmail, String password) {
		String subject = "[Dwell-In] 임시 비밀번호 발금";

		String text = "임시 비밀번호로 로그인한 후 비밀번호를 변경해주세요. \n\n 임시 비밀번호 : " + password;

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);
	}
}
