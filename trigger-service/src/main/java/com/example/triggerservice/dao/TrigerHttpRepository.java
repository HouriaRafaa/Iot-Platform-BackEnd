package com.example.triggerservice.dao;

import com.example.triggerservice.entities.React;
import com.example.triggerservice.entities.Triger;
import com.example.triggerservice.entities.TrigerHttp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface TrigerHttpRepository extends JpaRepository<TrigerHttp,Long> {

    public TrigerHttp findTrigerHttpByReact(React react);
    public TrigerHttp findTrigerHttpByTriger(Triger triger);
}
