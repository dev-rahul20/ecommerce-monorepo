package com.ecommerce.security.relay;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;

import reactor.core.publisher.Mono;

/** Adds the outbound Authorization header to WebClient calls that do not set one. */
public class JwtRelayExchangeFilterFunction implements ExchangeFilterFunction {

	private final OutboundTokenProvider tokens;

	public JwtRelayExchangeFilterFunction(OutboundTokenProvider tokens) {
		this.tokens = tokens;
	}

	@Override
	public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
		if (request.headers().containsKey(HttpHeaders.AUTHORIZATION)) {
			return next.exchange(request);
		}
		return next.exchange(ClientRequest.from(request)
										  .header(HttpHeaders.AUTHORIZATION, tokens.authorizationHeader())
										  .build());
	}
}
