package com.example.triggerservice.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Commande {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String valeur;

    private boolean executed;


    @JsonBackReference
    @ManyToOne
    private Triger triger;

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", valeur='" + valeur + '\'' +
                ", executed=" + executed +
                '}';
    }
}
