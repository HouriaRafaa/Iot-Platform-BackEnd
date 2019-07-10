package com.example.triggerservice.dao;

import com.example.triggerservice.entities.React;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RepositoryRestResource
public interface ReactRepository extends JpaRepository<React,Long> {

//    @Modifying
//    @Query(value = "insert into React (nom, cond, val, appUser,canal,field) values (:nom, :cond, :val, :appUser,:canal,:field)",
//    nativeQuery = true)
//    public void saveReact(@Param("nom") String nom, @Param("cond") String cond, @Param("val") double val,
//                     @Param("appUser") Long appUser, @Param("canal") String canal, @Param("field") String field);



    public List<React> findReactByField(int field);


    React findReactById(Long id);
    public List<React>findReactByAppUser(Long userId);

}
