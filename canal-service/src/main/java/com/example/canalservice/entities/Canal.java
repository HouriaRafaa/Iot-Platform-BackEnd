package com.example.canalservice.entities;

import com.example.canalservice.models.AppUser;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Canal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private Date dateCreation;

    private String cleLecture;
    private String cleEcriture;


    private Long appUser;


    @JsonManagedReference
    @OneToMany(mappedBy ="canal",cascade = CascadeType.ALL)
    private Collection<Field> fields = new ArrayList<>();

}
