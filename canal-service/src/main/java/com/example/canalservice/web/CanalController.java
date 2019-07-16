package com.example.canalservice.web;

import com.example.canalservice.dao.CanalRepository;
import com.example.canalservice.dao.FieldRepository;
import com.example.canalservice.dao.ValeurRepository;
import com.example.canalservice.entities.Canal;
import com.example.canalservice.entities.Field;
import com.example.canalservice.entities.Valeur;
import com.example.canalservice.models.AppUser;
import com.example.canalservice.service.CanalService;
import com.example.canalservice.service.NextSequenceService;
import javafx.application.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.*;

@RestController
public class CanalController {

    @Autowired
    CanalRepository canalRepository;

    @Autowired
    CanalService canalService;

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ValeurRepository valeurRepository;

    @Autowired
    private NextSequenceService nextSequenceService;


    @RequestMapping(value = "/add-canal", method = RequestMethod.POST)
    public void create(@RequestBody Map<String, Object> payload, HttpServletRequest request) throws Exception {

        Canal canal;
//        System.out.println("http://localhost:8091/appUsers/"+String.valueOf(payload.get("userId")));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("content-type","application/json");

        ResponseEntity<AppUser> responseEntity=restTemplate.exchange("http://authentification-service/appUsers/"+String.valueOf(payload.get("userId")),
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                AppUser.class
                );


        System.out.println("UserId"+responseEntity.getBody().getId());
        Long userId=responseEntity.getBody().getId();


        canal= canalService.saveCanal(
                payload.get("nom").toString(),
                payload.get("description").toString(),
             userId,
                null
        );
        canal.setCleEcriture(UUID.randomUUID().toString().replaceAll("-","").toUpperCase());
        canal.setCleLecture(UUID.randomUUID().toString().replaceAll("-","").toUpperCase());
        canal.setDateCreation(new Date());
        canalRepository.save(canal);


        ArrayList<Field>myFields=new ArrayList<>();

        payload.forEach((s, o) -> {
            if (s.contains("field")){
               Field f= fieldRepository.save(new Field(nextSequenceService.getNextSequence("customSequences2"),o.toString(), canal, null));
               myFields.add(f);
               canal.setFields(myFields);
               canalRepository.save(canal);
               }
        });


    }
    @RequestMapping(value = "/canals/{id}", method = RequestMethod.POST)
    public void update(@RequestBody Map<String, Object> payload, @PathVariable int id) throws Exception {

        ArrayList<Field> fields = new ArrayList<>();
        payload.forEach((s, o) -> {
            if (s.equals("fields")) {
                System.out.println("filed now " + o);
                //Check if fields object has at least one field
                if (o.toString().contains("{")){
                    o = o.toString().replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\\{", "").replaceAll("\\}","");
                    String[] kvPairs = o.toString().split(",");
                    int fieldId = 0;
                    for (String kvPair : kvPairs) {
                        String[] kv = kvPair.split("=");
                        String key = kv[0];
                        String value = kv[1];
                        if(key.contains("id")) {
                            if (value.contains("-1")) {
                                fieldId = -1;
                            } else {
                                fieldId =Integer.parseInt(value);

                            }
                        }
                        if (key.contains("nom")){
                            fields.add(new Field(fieldId, value));
                        }

                    }
                }
            }
        });


        System.out.println("payload ======" +  payload.get("fields"));


        canalService.updateCanal(
                id,
                payload.get("nom").toString(),
                payload.get("description").toString(),
                fields
        );
    }



    @RequestMapping(value = "/canals/{id}",method = RequestMethod.DELETE)
    public void deleteCanal(@PathVariable int id){

        Canal canal= canalRepository.findCanalByCanalId(id);

        List<Field>fields=fieldRepository.findFieldByCanal(canal);

        for(Field f: fields){
          List<Valeur>  valeurs= valeurRepository.findValeurByField(f);
          for(Valeur v : valeurs){
              valeurRepository.delete(v);
          }
        }


        fieldRepository.deleteAll(fields);

        if(canal!=null){
            canalRepository.delete(canal);
        }
    }



    @RequestMapping(value = "/Allcanals/{id}",method = RequestMethod.GET)

    public Canal getCanal(@PathVariable int id) {
        Canal canal = canalRepository.findCanalByCanalId(id);

        if (canal != null) {
            return canal;

        }
      return  null;
    }


    @RequestMapping(value = "/Allcanals/{id}/fields",method = RequestMethod.GET)

    public List<Field> getField(@PathVariable int id) {

        Canal canal = canalRepository.findCanalByCanalId(id);

        if (canal != null) {
            return  (List<Field>) canal.getFields();

        }
        return  null;
    }




    @RequestMapping(value = "/canals/{UserId}",method = RequestMethod.GET)

    public List<Canal> getUserCanal(@PathVariable Long UserId){

         return canalRepository.findCanalByAppUser(UserId);

    }

  //canals/"+payload.get("CanalId")+"/fields/"+payload.get("fieldId")
    @RequestMapping(value ="/canals/{canalId}/field/{fieldId}" ,method = RequestMethod.GET)

    public Field findMyfield(@PathVariable int canalId,@PathVariable int fieldId){

        Canal c = canalRepository.findCanalByCanalId(canalId);
        List<Field> fields=fieldRepository.findFieldByCanal(c);

        for(Field f :fields){
           if(f.getFieldId()==fieldId){
               System.out.println("myyyyyyyyyfileeeees "+f);
               return f;
           }
        }

        return null;
    }


    @RequestMapping(value = "/fields/{fieldId}")
    Field getMyfield(@PathVariable int fieldId){

        return fieldRepository.findFieldByFieldId(fieldId);
    }

    @RequestMapping(value = "/canals/{canalId}/fields")

    public List<Field> getMyfields(@PathVariable int canalId){

      Canal c = canalRepository.findCanalByCanalId(canalId);

      List<Field> fields = fieldRepository.findFieldByCanal(c);

      System.out.println("mys " +fields);
      return  fields;

    }


    @RequestMapping(value = "/Mycanal/{id}",method = RequestMethod.GET)
    public Canal getMyCanal(@PathVariable int id ){

        Canal c = canalRepository.findCanalByCanalId(id);

        return c;
    }


    @RequestMapping(value = "/canals",method = RequestMethod.GET)

    public List<Canal> getCanals() {

        List<Canal> canals = canalRepository.findAll();

        if (canals.size() >0) {
            return canals;

        }
        return  null;
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Valeur2> readData(@RequestParam Map<String,String> allParams ) {

        List<Valeur> list=null;
        List<Valeur2> valeur = new ArrayList<Valeur2>();

        Canal canal = canalRepository.findCanalByCleLecture(allParams.get("key"));

        if ( canal!=null && canal.getFields().size()!=0) {
            for(Field field:canal.getFields()){
                if (field.getNom().equalsIgnoreCase(allParams.get("field"))){
                    list = new ArrayList(field.getValeur());

                    for(Valeur v:list){
                        Valeur2 valeur2 = new Valeur2(v.getId(),v.getValeur(),v.getDate());
                        valeur.add(valeur2);
                    }
                    return valeur;
                }

            }
        }
        return valeur;
    }




}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Valeur2{
    String id;
    Double valeur;
    Date date;

}

