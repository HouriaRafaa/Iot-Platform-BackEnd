package com.example.triggerservice.dao;
import com.example.triggerservice.entities.Triger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TrigerRepository extends JpaRepository<Triger,Long> {

    public Triger findTrigerByApikey(String apikey);

    public Triger findTrigerById(Long id);


    public List<Triger> findTrigerByUser(Long user);


}
