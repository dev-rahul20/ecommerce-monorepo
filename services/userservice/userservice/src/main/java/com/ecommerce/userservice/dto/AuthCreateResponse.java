package com.ecommerce.userservice.dto;

public record AuthCreateResponse(Boolean success, String status, String message, Object data) {}
