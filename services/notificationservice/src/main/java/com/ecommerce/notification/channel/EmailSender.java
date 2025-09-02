package com.ecommerce.notification.channel;

public interface EmailSender {
	
	void send(String to, String subject, String body) throws Exception;

}
