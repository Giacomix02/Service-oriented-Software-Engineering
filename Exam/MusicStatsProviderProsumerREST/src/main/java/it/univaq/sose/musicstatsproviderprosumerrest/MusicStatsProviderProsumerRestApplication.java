package it.univaq.sose.musicstatsproviderprosumerrest;
    
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableJpaAuditing
@SpringBootApplication
@EnableDiscoveryClient
public class MusicStatsProviderProsumerRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicStatsProviderProsumerRestApplication.class, args);
    }

}
