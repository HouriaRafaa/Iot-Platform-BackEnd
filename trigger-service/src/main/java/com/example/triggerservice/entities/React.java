package com.example.triggerservice.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Data @NoArgsConstructor
public class React {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String cond;

    private double val;

    private boolean activated;

    private Long appUser;

    private int canal;


    private int field;



    public React(List<String> conditions) {
         conditions = new ArrayList<>();
        conditions.add("IS greather then");
        conditions.add("IS less then");
        conditions.add("IS equal");

    }
}
