package com.example.canalservice.web;

import com.example.canalservice.dao.CanalRepository;
import com.example.canalservice.dao.FieldRepository;
import com.example.canalservice.entities.Canal;
import com.example.canalservice.entities.Field;
import com.example.canalservice.entities.Valeur;
import com.example.canalservice.models.AppUser;
import com.example.canalservice.service.CanalService;
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




    @RequestMapping(value = "/add-canal", method = RequestMethod.POST)
    public void create(@RequestBody Map<String, Object> payload, HttpServletRequest request) throws Exception {

        Canal canal;
//        System.out.println("http://localhost:8091/appUsers/"+String.valueOf(payload.get("userId")));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("content-type","application/json");

        ResponseEntity<AppUser> responseEntity=restTemplate.exchange("http://localhost:8096/appUsers/"+String.valueOf(payload.get("userId")),
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

        payload.forEach((s, o) -> {
            if (s.contains("field")){
                fieldRepository.save(new Field(o.toString(), canal, null));
            }
        });
    }
    @RequestMapping(value = "/canals/{id}", method = RequestMethod.POST)
    public void update(@RequestBody Map<String, Object> payload, @PathVariable long id) throws Exception {

        ArrayList<Field> fields = new ArrayList<>();
        payload.forEach((s, o) -> {
            if (s.equals("fields")) {
                System.out.println("filed now " + o);
                //Check if fields object has at least one field
                if (o.toString().contains("{")){
                    o = o.toString().replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\\{", "").replaceAll("\\}","");
                    String[] kvPairs = o.toString().split(",");
                    long fieldId = 0;
                    for (String kvPair : kvPairs) {
                        String[] kv = kvPair.split("=");
                        String key = kv[0];
                        String value = kv[1];
                        if(key.contains("id")) {
                            if (value.contains("-1")) {
                                fieldId = -1;
                            } else {
                                fieldId = Long.parseLong(value);

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
    public void deleteCanal(@PathVariable Long id){

        Canal canal= canalRepository.findCanalById(id);

        if(canal!=null){
            canalRepository.delete(canal);
        }
    }



    @RequestMapping(value = "/Allcanals/{id}",method = RequestMethod.GET)

    public Canal getCanal(@PathVariable Long id) {

        Canal canal = canalRepository.findCanalById(id);

        if (canal != null) {
            return canal;

        }
      return  null;
    }


    @RequestMapping(value = "/Allcanals/{id}/fields",method = RequestMethod.GET)

    public List<Field> getField(@PathVariable Long id) {

        Canal canal = canalRepository.findCanalById(id);

        if (canal != null) {
            return  (List<Field>) canal.getFields();

        }
        return  null;
    }

    @RequestMapping(value = "/canals/{UserId}",method = RequestMethod.GET)

    public List<Canal> getUserCanal(@PathVariable Long UserId){

        List<Canal> canals=canalRepository.findCanalByAppUser(UserId);

        return canals;
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


    Long id;
    Double valeur;
    Date date;





}

