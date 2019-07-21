package com.example.triggerservice.service;

import com.example.triggerservice.dao.TimeControlRepository;
import com.example.triggerservice.dao.TrigerRepository;
import com.example.triggerservice.entities.Commande;
import com.example.triggerservice.entities.TimeControl;
import com.example.triggerservice.entities.Triger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TriggerServiceImpl implements TriggerService {

    @Autowired
    TrigerRepository trigerRepository;

    @Autowired
    TimeControlRepository timeControlRepository;
    @Override
    public Triger saveTrigger(String nom, Long user, List<Commande> commandes) {

        Triger triger =new Triger();

        triger.setNom(nom);
        triger.setApikey(UUID.randomUUID().toString().replaceAll("-","").toUpperCase());
        triger.setUser(user);
        triger.setCommandes(commandes);

        trigerRepository.save(triger);
        return triger;
    }

    @Override
    public TimeControl saveTimeControl(String nom, Date dateAction,int hour,int min,Long idUser) {
        TimeControl timeControl = new TimeControl();
        timeControl.setNom(nom);
        timeControl.setDateAction(dateAction);
        timeControl.setHour(hour);
        timeControl.setMinutes(min);
        timeControl.setIdUser(idUser);
        timeControlRepository.save(timeControl);
        return timeControl;
    }
}
