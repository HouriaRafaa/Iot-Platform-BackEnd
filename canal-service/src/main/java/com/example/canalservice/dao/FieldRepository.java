package com.example.canalservice.dao;

import com.example.canalservice.entities.Canal;
import com.example.canalservice.entities.Field;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface FieldRepository extends MongoRepository<Field,String> {

    public Field findFieldById(String id);
    public List<Field> findFieldByCanal(Canal canal);


       Field findFieldByFieldId(int id );

       Field findFieldByCanal(int d );

       void deleteFieldByFieldId(int id);



}
