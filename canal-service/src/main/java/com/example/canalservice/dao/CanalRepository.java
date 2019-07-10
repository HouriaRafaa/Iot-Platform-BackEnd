package com.example.canalservice.dao;




import com.example.canalservice.entities.Canal;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CanalRepository extends MongoRepository<Canal,String> {


    public Canal findCanalById(String id);

    public  Canal findCanalByCanalId(int id);

    public Canal findCanalByCleEcriture(String cleEcriture);

    public Canal findCanalByCleLecture(String cleLecture);

    public List<Canal> findCanalByAppUser(Long id);


}
