package com.example.demo.dao;

import com.example.demo.entities.CreditProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CreditProduitRepository extends JpaRepository<CreditProduit, Long> {

    //public List  <CreditProduit> findAllBy() ;
    public CreditProduit findCreditProduitById(Long id) ;
}
