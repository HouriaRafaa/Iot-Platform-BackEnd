package com.example.canalservice.entities;

import com.example.canalservice.models.AppUser;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Document
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class Canal {

    @Id
    private String id;

    private int canalId;

    private String nom;
    private String description;
    private Date dateCreation;

    private String cleLecture;
    private String cleEcriture;

    private Long appUser;

    @Override
    public String toString() {
        return "Canal{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @JsonManagedReference
   @DBRef
    private Collection<Field> fields = new ArrayList<>();

}
