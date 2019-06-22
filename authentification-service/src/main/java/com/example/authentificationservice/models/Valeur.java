package com.example.authentificationservice.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data @NoArgsConstructor
public class Valeur {


    private Long id;
    private double valeur;
    private Date date;

    private Field field;


    public Valeur(double valeur, Field field , Date date) {
        this.valeur = valeur;
        this.field = field;
        this.date =date;
    }

}
