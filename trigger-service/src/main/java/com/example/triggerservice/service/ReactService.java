package com.example.triggerservice.service;


import com.example.triggerservice.entities.React;
import com.example.triggerservice.entities.TrigerEmail;

public interface ReactService {


    React saveReact(String nom, String condition, double valeur, Long canal, Long field,Long appUser);

    void EnvoyerEmail(TrigerEmail trigerEmail);



}
