package com.example.authentificationservice.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class AppUser {


   @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public  AppUser(Long id){
     this.id=id;
    }

    @Column(unique = true)
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String password;

    @Column(unique = true)
    private String email;
    private  boolean actived;



    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles = new ArrayList<>();


}
