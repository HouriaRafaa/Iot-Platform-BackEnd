package com.example.triggerservice.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data @NoArgsConstructor @AllArgsConstructor
public class AppRole {

   private Long id;
   private String roleName;



}
