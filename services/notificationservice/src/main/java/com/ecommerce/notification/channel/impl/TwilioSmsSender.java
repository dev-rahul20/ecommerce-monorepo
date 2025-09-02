package com.ecommerce.notification.channel.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ecommerce.notification.channel.SmsSender;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TwilioSmsSender implements SmsSender {

	@Value("${twilio.fromNumber:+10000000000}")
	private String from;

	@Override
	public void send(String toPhone, String body) {
		Message.creator(new PhoneNumber(toPhone), new PhoneNumber(from), body).create();
	}

}
