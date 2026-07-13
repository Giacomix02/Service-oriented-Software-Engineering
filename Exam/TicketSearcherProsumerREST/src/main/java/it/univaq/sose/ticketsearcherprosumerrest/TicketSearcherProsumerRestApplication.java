package it.univaq.sose.ticketsearcherprosumerrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TicketSearcherProsumerRestApplication {
	public static void main(String[] args) {
		SpringApplication.run(TicketSearcherProsumerRestApplication.class, args);
	}
}