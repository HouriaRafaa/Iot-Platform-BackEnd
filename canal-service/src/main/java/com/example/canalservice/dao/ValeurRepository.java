package com.example.canalservice.dao;


import com.example.canalservice.entities.Valeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ValeurRepository extends JpaRepository<Valeur,Long> {
}
