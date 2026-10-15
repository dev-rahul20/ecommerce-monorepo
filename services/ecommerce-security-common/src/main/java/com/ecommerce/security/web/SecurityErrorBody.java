package com.ecommerce.security.web;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Error body in the same shape the services already return: success, status, message, data. */
public final class SecurityErrorBody {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private SecurityErrorBody() {
	}

	public static byte[] json(String status, String message) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("success", false);
		body.put("status", status);
		body.put("message", message);
		body.put("data", null);
		try {
			return MAPPER.writeValueAsBytes(body);
		} catch (JsonProcessingException ex) {
			throw new IllegalStateException(ex);
		}
	}
}
