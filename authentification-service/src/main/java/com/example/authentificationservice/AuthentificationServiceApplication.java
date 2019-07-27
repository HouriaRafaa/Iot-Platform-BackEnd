package com.example.authentificationservice;

import com.example.authentificationservice.dao.AppRoleRepository;
import com.example.authentificationservice.entities.AppRole;
import com.example.authentificationservice.entities.AppUser;
import com.example.authentificationservice.service.AccountService;
import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
@EnableEmailTools

public class AuthentificationServiceApplication implements RepositoryRestConfigurer,CommandLineRunner {


	@Bean
	BCryptPasswordEncoder getBCPE(){
		return  new BCryptPasswordEncoder();
	}
	public static void main(String[] args) {
		SpringApplication.run(AuthentificationServiceApplication.class, args);
	}


	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@Autowired
	AccountService accountService;
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	@Autowired
	private AppRoleRepository appRoleRepository;

	@Override
	public void run(String... args) throws Exception {

		repositoryRestConfiguration.exposeIdsFor(AppUser.class,AppRole.class);

		if(appRoleRepository.count()==0)
		{
			accountService.saveRole(new AppRole(null,"USER"));
			accountService.saveRole(new AppRole(null,"ADMIN"));

		}




	}
}
