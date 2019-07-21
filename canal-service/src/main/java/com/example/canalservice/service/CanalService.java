package com.example.canalservice.service;

import com.example.canalservice.entities.Canal;
import com.example.canalservice.entities.Field;

import java.util.List;

public interface CanalService {


     Canal saveCanal(String nom, String description, Long appUser, List<Field> fields);

     void updateCanal(int id, String nom, String description, List<Field> fields);

}




