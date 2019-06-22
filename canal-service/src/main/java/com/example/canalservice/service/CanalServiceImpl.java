package com.example.canalservice.service;


import com.example.canalservice.dao.CanalRepository;
import com.example.canalservice.dao.FieldRepository;
import com.example.canalservice.entities.Canal;
import com.example.canalservice.entities.Field;
import com.example.canalservice.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CanalServiceImpl implements CanalService {


    @Autowired
    CanalRepository canalRepository;
    @Autowired
    FieldRepository fieldRepository;

    @Override
    public Canal saveCanal(String nom, String description, Long user, List<Field> fields) {
        Canal canal = new Canal();
       canal.setNom(nom);
       canal.setDescription(description);
       canal.setAppUser(user);
        canal.setFields(fields);
        return canal;
    }

    @Override
    public void updateCanal(long id, String nom, String description, List<Field> fields) {
        Canal canal = canalRepository.findCanalById(id);
        this.setCanalInfo(canal, nom, description);
        List<Field> fieldList = fieldRepository.findFieldByCanal(canal);
        ArrayList<Long> tableId = new ArrayList<>();

        for(Field field: fields){
            if(!(field.getId() == -1)){
                Field f = fieldRepository.findFieldById(field.getId());
                f.setNom(field.getNom());
                fieldRepository.save(f);
                tableId.add(field.getId());
            }else{
                fieldRepository.save(new Field(field.getNom(), canal, null));
            }
        }

        for(Field field: fieldList){
            if(!tableId.contains(field.getId())){
                fieldRepository.deleteById(field.getId());
            }
        }

        canalRepository.save(canal);
    }


    public void setCanalInfo(Canal canal, String nom, String description) {
        canal.setNom(nom);
        canal.setDescription(description);
        canalRepository.save(canal);
    }

}
