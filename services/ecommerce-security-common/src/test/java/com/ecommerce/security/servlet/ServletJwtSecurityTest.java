package com.ecommerce.security.servlet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.security.jwt.AuthenticatedUser;
import com.ecommerce.security.jwt.JwtTokenService;

@SpringBootTest(properties = {
		"JWT_SECRET=test-secret-that-is-at-least-32-bytes-long!",
		"ecommerce.security.public-paths=POST /register,/hello/public" })
@AutoConfigureMockMvc
class ServletJwtSecurityTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	JwtTokenService tokens;

	@Test
	void rejectsRequestWithoutToken() throws Exception {
		mvc.perform(get("/hello"))
		   .andExpect(status().isUnauthorized())
		   .andExpect(jsonPath("$.success").value(false))
		   .andExpect(jsonPath("$.status").value("UNAUTHORIZED"));
	}

	@Test
	void rejectsInvalidToken() throws Exception {
		mvc.perform(get("/hello").header("Authorization", "Bearer not-a-jwt"))
		   .andExpect(status().isUnauthorized())
		   .andExpect(jsonPath("$.message").value("Invalid token"));
	}

	@Test
	void acceptsValidToken() throws Exception {
		String token = tokens.issueAccessToken("rahul", 7L, 3L, List.of("USER"));

		mvc.perform(get("/hello").header("Authorization", "Bearer " + token))
		   .andExpect(status().isOk())
		   .andExpect(content().string("hello rahul 7"));
	}

	@Test
	void publicPathsNeedNoToken() throws Exception {
		mvc.perform(post("/register")).andExpect(status().isOk());
		mvc.perform(get("/hello/public")).andExpect(status().isOk());
	}

	@Test
	void publicPathIsMethodSpecific() throws Exception {
		mvc.perform(get("/register")).andExpect(status().isUnauthorized());
	}

	@Test
	void roleChecksReturnForbidden() throws Exception {
		String user = tokens.issueAccessToken("rahul", 7L, 3L, List.of("USER"));
		String service = tokens.issueServiceToken("user-service");

		mvc.perform(post("/internal").header("Authorization", "Bearer " + user)).andExpect(status().isForbidden());
		mvc.perform(post("/internal").header("Authorization", "Bearer " + service)).andExpect(status().isOk());
	}

	@Test
	void healthIsOpen() throws Exception {
		mvc.perform(get("/actuator/health")).andExpect(status().isNotFound());
	}

	@SpringBootApplication
	@RestController
	static class TestApp {

		@GetMapping("/hello")
		String hello(@AuthenticationPrincipal AuthenticatedUser user) {
			return "hello " + user.username() + " " + user.userId();
		}

		@GetMapping("/hello/public")
		String open() {
			return "open";
		}

		@PostMapping("/register")
		String register() {
			return "registered";
		}

		@GetMapping("/register")
		String registerGet() {
			return "nope";
		}

		@PostMapping("/internal")
		@PreAuthorize("hasRole('SERVICE')")
		String internal() {
			return "ok";
		}
	}
}
