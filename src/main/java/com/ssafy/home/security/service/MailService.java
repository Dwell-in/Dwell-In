package com.ssafy.home.security.service;

import org.springframework.beans.factory.annotation.Value;
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

	public void sendVerificationEmail(String toEmail, String token, String frontUrl) {
	    try {
	        String subject = "[Dwell-In] 이메일 인증 요청";
	        String verificationUrl = frontUrl + "/#/email/verify?token=" + token;

	        String htmlContent = """
	        	    <div style="font-family: Arial, sans-serif; padding: 20px; background-color: #f8f9fa; border-radius: 8px; color: #212529;">
	        	        <h2 style="color: #343a40;">[Dwell-In] 이메일 인증 요청</h2>
	        	        <p>안녕하세요,</p>
	        	        <p>아래 버튼을 클릭하여 이메일 인증을 완료해주세요.</p>
	        	        <a href="%s" style="display: inline-block; margin-top: 16px; padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px;">이메일 인증하기</a>
	        	        <p style="margin-top: 30px; font-size: 0.9em;">버튼이 작동하지 않는다면 아래 링크를 복사해서 브라우저에 붙여넣어 주세요:</p>
	        	        <p style="word-break: break-all;"><a href="%s">%s</a></p>
	        	    </div>
	        	""".formatted(verificationUrl, verificationUrl, verificationUrl);

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
