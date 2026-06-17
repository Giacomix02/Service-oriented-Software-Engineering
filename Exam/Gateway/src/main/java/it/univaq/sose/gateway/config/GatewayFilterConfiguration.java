package it.univaq.sose.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.server.mvc.filter.FilterSupplier;
import org.springframework.cloud.gateway.server.mvc.filter.SimpleFilterSupplier;

import it.univaq.sose.gateway.filter.GatewayPathLoggingFilters;

@Configuration
public class GatewayFilterConfiguration {

	@Bean
	public FilterSupplier gatewayPathLoggingFilterSupplier() {
		return new SimpleFilterSupplier(GatewayPathLoggingFilters.class);
	}

}

