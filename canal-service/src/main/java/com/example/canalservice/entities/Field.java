package com.example.canalservice.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import sun.reflect.CallerSensitive;


import java.util.ArrayList;
import java.util.Collection;

@Document
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class Field {


    @Id
    private String id;

    private int fieldId;

    private String nom;

    @Override
    public String toString() {
        return "Field{" +
                "id='" + id + '\'' +
                ", fieldId=" + fieldId +
                ", nom='" + nom + '\'' +
                '}';
    }

    @JsonBackReference
    @DBRef
    private Canal canal;


    @JsonManagedReference
    @DBRef
    private Collection<Valeur> valeur = new ArrayList<>();


    public Field(int fieldId, String nom, Canal canal, Collection<Valeur> valeur) {
        this.fieldId=fieldId;
        this.nom = nom;
        this.canal = canal;
        this.valeur = valeur;
    }


    public Field(String nom, Canal canal, Collection<Valeur> valeur) {

        this.nom = nom;
        this.canal = canal;
        this.valeur = valeur;
    }
    public Field(int id,String nom){
        this.fieldId=id;
        this.nom=nom;
    }


}
