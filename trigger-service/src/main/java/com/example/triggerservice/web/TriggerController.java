package com.example.triggerservice.web;

import com.example.triggerservice.dao.CommandeRepository;
import com.example.triggerservice.dao.TrigerRepository;
import com.example.triggerservice.entities.Commande;
import com.example.triggerservice.entities.Triger;
import com.example.triggerservice.models.AppUser;
import com.example.triggerservice.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
public class TriggerController {


    @Autowired
    TriggerService triggerService;

    @Autowired
    TrigerRepository trigerRepository;

    @Autowired
    CommandeRepository commandeRepository;

    @Autowired
    RestTemplate restTemplate;

@RequestMapping(value = "/trigger")

    public void addTrigger(@RequestBody Map<String, Object> payload,HttpServletRequest request){

    Triger triger;

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", request.getHeader("Authorization"));
    headers.add("content-type","application/json");

    ResponseEntity<AppUser> responseEntity=restTemplate.exchange("http://localhost:8096/appUsers/"+String.valueOf(payload.get("userId")),
            HttpMethod.GET,
            new HttpEntity<>("parameters", headers),
            AppUser.class
    );

    Long userId=responseEntity.getBody().getId();


    triger =triggerService.saveTrigger(
              payload.get("nom").toString(),
              userId,
              null);

        trigerRepository.save(triger);

       payload.forEach((s, o) -> {
        if (s.contains("commande")){

            commandeRepository.save(new Commande(null,o.toString(),false, triger));
        }
    });

    }

    @RequestMapping(method = RequestMethod.POST,value = "/trigers/{trigerId}")
    public void updateTrigger(@RequestBody Map<String,Object> payload, @PathVariable Long trigerId){

                Triger triger= trigerRepository.findById(trigerId).get();
                if(triger!=null) {

                    payload.forEach((s, o) -> {
                        if(s.contains("commande")) {
                            commandeRepository.save(new Commande(null, o.toString(), false, triger));

                        }});
                }


    }

    @RequestMapping(method = RequestMethod.GET,value = "/trigers/{trigerId}")

    public Triger getTriger(@PathVariable Long trigerId){

    Triger t=trigerRepository.findTrigerById(trigerId);
    return t;



    }





    @RequestMapping(value = "/ExecuteCommands/{id}",method = RequestMethod.GET)
    public String executeCommande(@PathVariable Long id){
        Triger triger=trigerRepository.findById(id).get();
        Commande c = new Commande();
        if(triger!=null && triger.getCommandes().size()!=0){
            for(Commande commande:triger.getCommandes()) {

                if(!commande.isExecuted()) {
                    commande.setExecuted(true);
                    commandeRepository.save(commande);
                    c = commande;
                }

            }

        }
        return c.getValeur();

    }


    @RequestMapping(value = "/userTriger/{id}",method = RequestMethod.GET)
    public List<Triger> getTrigerUser(@PathVariable Long id){

       List<Triger>trigers=trigerRepository.findTrigerByUser(id);

       return trigers;


    }


}

