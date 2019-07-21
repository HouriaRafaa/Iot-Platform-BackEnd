package com.example.canalservice.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Document
@Data @NoArgsConstructor
public class Valeur {

    @Id
    private String id;
    private double valeur;
    private Date date;

    @JsonBackReference
    @DBRef
    private Field field;

    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date dateYear ;

    public Valeur(double valeur, Field field , Date date) {
        this.valeur = valeur;
        this.field = field;
        this.date =date;
    }
    public Valeur(double valeur, Field field , Date date , Date dateYear) {
        this.valeur = valeur;
        this.field = field;
        this.date =date;
        this.dateYear = dateYear ;
    }


}
