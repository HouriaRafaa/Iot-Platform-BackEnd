package com.example.triggerservice.service;



import com.example.triggerservice.entities.Commande;
import com.example.triggerservice.entities.Triger;

import java.util.List;

public interface TriggerService {

    public Triger saveTrigger(String nom, Long user, List<Commande>
            commandes);
}
