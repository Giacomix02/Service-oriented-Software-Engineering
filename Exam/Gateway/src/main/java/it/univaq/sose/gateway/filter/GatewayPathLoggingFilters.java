package it.univaq.sose.gateway.filter;

import java.net.URI;
import java.util.LinkedHashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.server.mvc.common.MvcUtils;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.ServerResponse;

public final class GatewayPathLoggingFilters {

	private static final Logger log = LoggerFactory.getLogger(GatewayPathLoggingFilters.class);

	private GatewayPathLoggingFilters() {
	}

	public static HandlerFilterFunction<ServerResponse, ServerResponse> logPath() {
		return (request, next) -> {
			LinkedHashSet<URI> originalRequestUrls = MvcUtils.getAttribute(request,
				MvcUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
			URI routeBaseUri = MvcUtils.getAttribute(request, MvcUtils.GATEWAY_REQUEST_URL_ATTR);
			URI requestedUri = originalRequestUrls != null && !originalRequestUrls.isEmpty()
					? originalRequestUrls.iterator().next()
					: request.uri();
			URI convertedUri = request.uri();
			URI resolvedTargetUri = routeBaseUri != null
					? UriComponentsBuilder.fromUri(convertedUri)
						.scheme(routeBaseUri.getScheme())
						.host(routeBaseUri.getHost())
						.port(routeBaseUri.getPort())
						.build(true)
						.toUri()
					: convertedUri;

			log.info("Gateway route={} requestedPath={} convertedPath={} routeBase={} resolvedTarget={}",
				MvcUtils.getAttribute(request, MvcUtils.GATEWAY_ROUTE_ID_ATTR),
				requestedUri.getRawPath(),
				convertedUri.getRawPath(),
				routeBaseUri,
				resolvedTargetUri);
			System.out.println(String.format("Gateway route=%s requestedPath=%s convertedPath=%s routeBase=%s resolvedTarget=%s",
				MvcUtils.getAttribute(request, MvcUtils.GATEWAY_ROUTE_ID_ATTR),
				requestedUri.getRawPath(),
				convertedUri.getRawPath(),
				routeBaseUri,
				resolvedTargetUri));

			return next.handle(request);
		};
	}

}

