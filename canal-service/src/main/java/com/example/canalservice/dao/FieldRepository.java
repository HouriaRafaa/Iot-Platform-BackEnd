package com.example.canalservice.dao;

import com.example.canalservice.entities.Canal;
import com.example.canalservice.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface FieldRepository extends JpaRepository<Field,Long> {

    public Field findFieldById(Long id);
    public List<Field> findFieldByCanal(Canal canal);
}
