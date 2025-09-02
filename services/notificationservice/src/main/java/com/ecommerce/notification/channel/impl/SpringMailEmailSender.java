package com.ecommerce.notification.channel.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ecommerce.notification.channel.EmailSender;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringMailEmailSender implements EmailSender {

	private final JavaMailSender mailSender;

	@Override
	public void send(String to, String subject, String body) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(to);
		msg.setSubject(subject != null ? subject : "(no subject)");
		msg.setText(body);
		mailSender.send(msg);
	}

}
