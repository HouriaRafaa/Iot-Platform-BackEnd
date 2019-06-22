package com.example.triggerservice.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class TrigerHttp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commande;

   @ManyToOne
   private Triger triger;

    @ManyToOne
    private React react;


}
