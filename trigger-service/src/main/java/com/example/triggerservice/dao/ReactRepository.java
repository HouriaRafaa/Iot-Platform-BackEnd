package com.example.triggerservice.dao;

import com.example.triggerservice.entities.React;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ReactRepository extends JpaRepository<React,Long> {

    public List<React> findReactByField(Long field);

    public List<React>findReactByAppUser(Long userId);

}
