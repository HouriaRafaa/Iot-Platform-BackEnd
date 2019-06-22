package com.example.canalservice.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Field {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @JsonBackReference
    @ManyToOne
    private Canal canal;


    @JsonManagedReference
    @OneToMany(mappedBy = "field")
    private Collection<Valeur> valeur;


    public Field(String nom, Canal canal, Collection<Valeur> valeur) {
        this.nom = nom;
        this.canal = canal;
        this.valeur = valeur;
    }

    public Field(Long id,String nom){
        this.id=id;
        this.nom=nom;
    }


}
