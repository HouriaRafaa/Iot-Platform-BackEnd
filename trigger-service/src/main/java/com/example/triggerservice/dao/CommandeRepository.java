package com.example.triggerservice.dao;


import com.example.triggerservice.entities.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CommandeRepository extends JpaRepository<Commande,Long> {
}
