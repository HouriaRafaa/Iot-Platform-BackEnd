package com.example.triggerservice.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity @Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Triger {


    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String nom;


    private String apikey;

    private Long user;


    @JsonManagedReference
    @OneToMany(mappedBy = "triger")
    private Collection<Commande>commandes;


}
