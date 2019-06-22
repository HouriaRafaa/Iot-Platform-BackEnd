package com.example.triggerservice.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class TrigerAction {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String Tel;

    @ManyToOne
    private React react;



}
