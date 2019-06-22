package com.example.triggerservice.dao;

import com.example.triggerservice.entities.React;
import com.example.triggerservice.entities.TrigerEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TrigerEmailRepository extends JpaRepository<TrigerEmail,Long> {

    public TrigerEmail findTrigerEmailByReact(React react);
}
