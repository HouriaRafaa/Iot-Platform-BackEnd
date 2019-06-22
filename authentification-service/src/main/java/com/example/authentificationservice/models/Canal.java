package com.example.authentificationservice.models;

import com.example.authentificationservice.entities.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor
public class Canal {

    private Long id;
    private String nom;
    private String description;
    private Date dateCreation;

    private String cleLecture;
    private String cleEcriture;


    private AppUser appUser;


    private Collection<Field> fields = new ArrayList<>();

}
