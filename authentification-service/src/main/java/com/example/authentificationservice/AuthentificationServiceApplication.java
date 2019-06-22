package com.example.authentificationservice;

import com.example.authentificationservice.entities.AppRole;
import com.example.authentificationservice.entities.AppUser;
import com.example.authentificationservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@EnableDiscoveryClient
@SpringBootApplication
public class AuthentificationServiceApplication implements RepositoryRestConfigurer,CommandLineRunner {


	@Bean
	BCryptPasswordEncoder getBCPE(){
		return  new BCryptPasswordEncoder();
	}
	public static void main(String[] args) {
		SpringApplication.run(AuthentificationServiceApplication.class, args);
	}


	@Autowired
	AccountService accountService;
	@Autowired
	private RepositoryRestConfiguration repositoryRestConfiguration;

	@Override
	public void run(String... args) throws Exception {

		repositoryRestConfiguration.exposeIdsFor(AppUser.class,AppRole.class);

//		  accountService.saveRole(new AppRole(null,"USER"));
//		accountService.saveRole(new AppRole(null,"ADMIN"));




	}
}
