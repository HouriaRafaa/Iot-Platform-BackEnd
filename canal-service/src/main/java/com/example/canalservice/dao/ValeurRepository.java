package com.example.canalservice.dao;


import com.example.canalservice.entities.Field;
import com.example.canalservice.entities.Valeur;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ValeurRepository extends MongoRepository<Valeur,String> {


    List<Valeur> findValeurByField(Field field);
}
