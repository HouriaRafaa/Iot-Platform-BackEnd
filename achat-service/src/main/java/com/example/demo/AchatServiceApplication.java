package com.example.demo;

import com.example.demo.dao.AbonnementRepository;
import com.example.demo.dao.CreditProduitRepository;
import com.example.demo.entities.Abonnement;
import com.example.demo.entities.AppUser;
import com.example.demo.entities.CreditProduit;
import com.example.demo.service.AbonnementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@EnableDiscoveryClient
@SpringBootApplication
public class AchatServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AchatServiceApplication.class, args);
    }
    @Autowired
    private AbonnementService abonnementService;

    @Autowired
    CreditProduitRepository creditProduitRepository ;

    @Autowired
    private AbonnementRepository abonnementRepository ;

    @Autowired
    private RepositoryRestConfiguration repositoryRestConfiguration;

    @Override
    public void run(String... args) throws Exception{
        repositoryRestConfiguration.exposeIdsFor(CreditProduit.class,   AppUser.class,Abonnement.class);
        repositoryRestConfiguration.getCorsRegistry()
                .addMapping("/**").
                allowedOrigins("*").
                allowedHeaders("*").
                allowedMethods("OPTIONS","HEAD","GET","POST","PUT","DELETE","PATCH");

        if(abonnementRepository.count()==0) {
            Abonnement a = new Abonnement("Pro", 9000000, 10, 8,5000);
            Abonnement b = new Abonnement("Standard", 300000, 4, 4,0);
            abonnementService.saveAbonnement(a);
            abonnementService.saveAbonnement(b);
        }
        if (creditProduitRepository.count()==0) {
            creditProduitRepository.save(new CreditProduit(10000,1500)) ;
            creditProduitRepository.save(new CreditProduit(50000,5500)) ;
            creditProduitRepository.save(new CreditProduit(100000,8500)) ;
        }
    }

}
