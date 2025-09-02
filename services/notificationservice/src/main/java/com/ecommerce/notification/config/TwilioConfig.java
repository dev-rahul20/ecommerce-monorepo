package com.ecommerce.notification.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.twilio.Twilio;

import jakarta.annotation.PostConstruct;

@Configuration
public class TwilioConfig {

	@Value("${twilio.accountSid:}")
	private String accountSid;
	@Value("${twilio.authToken:}")
	private String authToken;

	@PostConstruct
	public void init() {
		if (!accountSid.isBlank() && !authToken.isBlank()) {
			Twilio.init(accountSid, authToken);
		}
	}

}
