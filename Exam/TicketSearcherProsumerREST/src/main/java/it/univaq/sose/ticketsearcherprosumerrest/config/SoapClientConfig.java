package it.univaq.sose.ticketsearcherprosumerrest.config;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.univaq.sose.ticketsearcherprosumerrest.soap.boxoffice.BoxOfficeService;

@Configuration
public class SoapClientConfig {

    @Value("${legacyboxoffice.url}")
    private String legacyBoxOfficeUrl;

    @Bean
    public BoxOfficeService boxOfficeServiceClient() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(BoxOfficeService.class);
        factory.setAddress(legacyBoxOfficeUrl);
        return (BoxOfficeService) factory.create();
    }
}