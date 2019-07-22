package com.example.triggerservice.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Triger {


    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String nom;


    private String apikey;

    private Long user;


    @JsonManagedReference
    @OneToMany(mappedBy = "triger")
    private Collection<Commande>commandes;

    @Override
    public String toString() {
        return "Triger{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", apikey='" + apikey + '\'' +
                ", user=" + user +
                '}';
    }
}
