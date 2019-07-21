package com.example.triggerservice.service;



import com.example.triggerservice.entities.Commande;
import com.example.triggerservice.entities.TimeControl;
import com.example.triggerservice.entities.Triger;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TriggerService {

    public Triger saveTrigger(String nom, Long user, List<Commande>
            commandes);

    TimeControl saveTimeControl(String nom, Date dateAction,int hour ,int min,int hourA,int minD,Long idUser);
}
