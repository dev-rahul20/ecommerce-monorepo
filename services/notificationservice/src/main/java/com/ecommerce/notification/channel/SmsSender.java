// com.ecommerce.notificationservice.channel.SmsSender.java
package com.ecommerce.notification.channel;

public interface SmsSender {
  void send(String toPhone, String body) throws Exception;
}
