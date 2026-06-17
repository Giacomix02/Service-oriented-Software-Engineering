package it.univaq.sose.gateway.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GatewayAccessLoggingFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(GatewayAccessLoggingFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String message = String.format("Gateway access method=%s requestPath=%s query=%s",
			request.getMethod(),
			request.getRequestURI(),
			request.getQueryString());
		log.info(message);
		System.out.println(message);
		filterChain.doFilter(request, response);
	}

}

