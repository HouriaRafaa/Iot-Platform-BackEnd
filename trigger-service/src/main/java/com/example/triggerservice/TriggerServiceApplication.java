package com.example.triggerservice;

import com.example.triggerservice.entities.Commande;
import com.example.triggerservice.entities.React;
import com.example.triggerservice.entities.Triger;
import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@EnableEmailTools


public class TriggerServiceApplication implements RepositoryRestConfigurer,CommandLineRunner {


	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(TriggerServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repositoryRestConfiguration.exposeIdsFor(React.class,Triger.class,Commande.class);


	}
}
