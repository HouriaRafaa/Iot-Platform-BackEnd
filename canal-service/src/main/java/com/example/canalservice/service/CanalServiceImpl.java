package com.example.canalservice.service;


import com.example.canalservice.dao.CanalRepository;
import com.example.canalservice.dao.FieldRepository;
import com.example.canalservice.entities.Canal;
import com.example.canalservice.entities.Field;
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

    @Autowired
    NextSequenceService nextSequenceService;

    @Override
    public Canal saveCanal(String nom, String description, Long user, List<Field> fields) {
        Canal canal = new Canal();


       canal.setNom(nom);
        canal.setCanalId(nextSequenceService.getNextSequence("customSequences"));

       canal.setDescription(description);
       canal.setAppUser(user);
        canal.setFields(fields);
        return canal;
    }

    @Override
    public void updateCanal(int id, String nom, String description, List<Field> fields) {
        Canal canal = canalRepository.findCanalByCanalId(id);
        this.setCanalInfo(canal, nom, description);
        List<Field> fieldList = fieldRepository.findFieldByCanal(canal);
        ArrayList<Integer> tableId = new ArrayList<>();

        for(Field field: fields){
            if(!(field.getFieldId() == -1)){
                Field f = fieldRepository.findFieldByFieldId(field.getFieldId());
                f.setNom(field.getNom());
                fieldRepository.save(f);
                tableId.add(field.getFieldId());
            }else{
               Field my= fieldRepository.save(new Field(nextSequenceService.getNextSequence("customSequences2"),field.getNom(), canal, null));

                canal.getFields().add(my);
                canalRepository.save(canal);
            }
        }
        for(Field field: fieldList){
            if(!tableId.contains(field.getFieldId())){
                fieldRepository.deleteFieldByFieldId(field.getFieldId());
                canal.getFields().remove(field);
                canalRepository.save(canal);

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
