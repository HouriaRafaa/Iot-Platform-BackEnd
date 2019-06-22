package com.example.triggerservice.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class TrigerEmail {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String message;

    @ManyToOne
    private React react;



}
