package com.example.demo.dao;

import com.example.demo.entities.Abonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AbonnementRepository extends JpaRepository<Abonnement, Long> {

    public Abonnement findAbonnementByOption(String typeAbonnement) ;
    public Abonnement findAbonnementById(long id) ;
}