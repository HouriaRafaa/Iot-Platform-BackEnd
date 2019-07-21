package com.example.triggerservice.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Commande {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String valeur;

    private boolean executed;

    @JsonBackReference
    @ManyToOne
    private Triger triger;


}
