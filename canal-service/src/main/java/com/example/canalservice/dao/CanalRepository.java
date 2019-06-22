package com.example.canalservice.dao;




import com.example.canalservice.entities.Canal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CanalRepository extends JpaRepository<Canal,Long> {


    public Canal findCanalById(Long id);

    public Canal findCanalByCleEcriture(String cleEcriture);

    public Canal findCanalByCleLecture(String cleLecture);

    public List<Canal> findCanalByAppUser(Long id);


}
