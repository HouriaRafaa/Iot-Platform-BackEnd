package com.example.canalservice.models;


import com.example.canalservice.entities.Canal;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

 @Data @AllArgsConstructor @NoArgsConstructor
public class AppUser {

    private Long id;
    private String userName;
    private long credit ;
//    private  String password;
    private String email;
    private  boolean actived;

    private Collection<AppRole> roles = new ArrayList<>();

// public AppUser(String userName, String password, boolean actived, Collection<Canal> canals, Collection<AppRole> roles) {
//  this.userName = userName;
//  this.password = password;
//  this.actived = actived;
//
//  this.roles = roles;
 //}
}
