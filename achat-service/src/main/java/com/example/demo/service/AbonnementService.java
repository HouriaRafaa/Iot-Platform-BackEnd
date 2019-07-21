package com.example.demo.service;



import com.example.demo.entities.Abonnement;

import java.util.ArrayList;

public interface AbonnementService {

    public void saveAbonnement(Abonnement abonnement) ;
    public Abonnement saveAbonnement(String option, long credit) ;
    public Abonnement findAbonnementByOption(String typeAbonnement) ;
    public Abonnement findAbonnementById(long id) ;
    public ArrayList<Abonnement> findAll() ;


}
