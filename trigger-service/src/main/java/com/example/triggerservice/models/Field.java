package com.example.triggerservice.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
 @Data @AllArgsConstructor @NoArgsConstructor
public class Field {

    private String id;
     private int fieldId;
    private String nom;

    private Canal canal;

    private Collection<Valeur> valeur;


    public Field(String nom, Canal canal, Collection<Valeur> valeur) {
        this.nom = nom;
        this.canal = canal;
        this.valeur = valeur;
    }

    public Field(String id,String nom){
        this.id=id;
        this.nom=nom;
    }


}
