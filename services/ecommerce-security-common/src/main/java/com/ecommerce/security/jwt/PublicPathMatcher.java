package com.ecommerce.security.jwt;

import java.util.List;
import java.util.Locale;

import org.springframework.util.AntPathMatcher;

/**
 * Matches requests against entries of the form {@code "/auth/login"} (any method)
 * or {@code "POST /user"} (one method). Shared by the gateway and the services.
 */
public class PublicPathMatcher {

	private static final AntPathMatcher MATCHER = new AntPathMatcher();

	private final List<Entry> entries;

	public PublicPathMatcher(List<String> publicPaths) {
		this.entries = publicPaths == null ? List.of()
				: publicPaths.stream().map(String::trim).filter(s -> !s.isEmpty()).map(Entry::parse).toList();
	}

	public boolean matches(String method, String path) {
		if (path == null || isSuspicious(path)) {
			return false;
		}
		return entries.stream().anyMatch(e -> e.matches(method, path));
	}

	/** Never treat traversal or encoded tricks as public, whatever the pattern. */
	private static boolean isSuspicious(String path) {
		String lower = path.toLowerCase(Locale.ROOT);
		return lower.contains("..") || lower.contains(";") || lower.contains("\\") || lower.contains("%2e")
				|| lower.contains("%2f") || lower.contains("%5c");
	}

	private record Entry(String method, String pattern) {

		static Entry parse(String raw) {
			int space = raw.indexOf(' ');
			if (space < 0) {
				return new Entry(null, raw);
			}
			return new Entry(raw.substring(0, space).trim().toUpperCase(Locale.ROOT), raw.substring(space + 1).trim());
		}

		boolean matches(String requestMethod, String path) {
			return (method == null || method.equalsIgnoreCase(requestMethod)) && MATCHER.match(pattern, path);
		}
	}
}
