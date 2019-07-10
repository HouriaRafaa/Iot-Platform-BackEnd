package com.example.canalservice.service;

import com.example.canalservice.entities.Canal;
import com.example.canalservice.entities.Field;
import com.example.canalservice.models.AppUser;

import java.util.Date;
import java.util.List;

public interface CanalService {


     Canal saveCanal(String nom, String description, Long appUser, List<Field> fields);

     void updateCanal(int id, String nom, String description, List<Field> fields);

}




