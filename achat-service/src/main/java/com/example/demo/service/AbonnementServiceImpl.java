package com.example.demo.service;


import com.example.demo.dao.AbonnementRepository;
import com.example.demo.entities.Abonnement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AbonnementServiceImpl implements AbonnementService {

    @Autowired
    private AbonnementRepository abonnementRepository ;

    @Override
    public void saveAbonnement(Abonnement abonnement) {
          abonnementRepository.save(abonnement);
    }

    @Override
    public Abonnement saveAbonnement(String option, long credit) {
        return null;
    }

    @Override
    public Abonnement findAbonnementByOption(String typeAbonnement) {
       return abonnementRepository.findAbonnementByOption(typeAbonnement);
    }

    @Override
    public Abonnement findAbonnementById(long id) {
        return abonnementRepository.findAbonnementById(id) ;
    }

    @Override
    public ArrayList<Abonnement> findAll() {
        return (ArrayList<Abonnement>) abonnementRepository.findAll();
    }

}
