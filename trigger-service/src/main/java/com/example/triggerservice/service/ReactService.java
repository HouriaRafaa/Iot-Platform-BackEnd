package com.example.triggerservice.service;


import com.example.triggerservice.entities.React;
import com.example.triggerservice.entities.TrigerEmail;

public interface ReactService {


    React saveReact(String nom, String condition, double valeur, Long appUser,int canal, int field);

    void EnvoyerEmail(TrigerEmail trigerEmail);



}
