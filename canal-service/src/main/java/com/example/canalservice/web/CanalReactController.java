package com.example.canalservice.web;


import com.example.canalservice.dao.CanalRepository;
import com.example.canalservice.dao.FieldRepository;
import com.example.canalservice.dao.ValeurRepository;
import com.example.canalservice.entities.Canal;
import com.example.canalservice.entities.Field;
import com.example.canalservice.entities.Valeur;
import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class CanalReactController {

    @Autowired
    CanalRepository canalRepository;

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    ValeurRepository valeurRepository;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/record")
    @ResponseBody

    public String updateCanal(@RequestParam Map<String,String> allParams, HttpServletRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("content-type","application/json");

        Canal canal = canalRepository.findCanalByCleEcriture(allParams.get("key"));

        Pusher pusher = new Pusher("762880", "84bee67aad46ed497369", "5017a5ee0387085255ae");
        pusher.setCluster("eu");
        pusher.setEncrypted(true);

        //  pusher.Triger("my-channel", "my-event", Collections.singletonMap("message", "hello world"));

        for (Field field : canal.getFields()) {
            for (Map.Entry<String, String> entry : allParams.entrySet()) {
                if (field.getNom().equalsIgnoreCase(entry.getKey())) {
                    try {
                        Double data = Double.parseDouble(entry.getValue());
                        Valeur valeur = new Valeur(data, field, new Date());
                        valeurRepository.save(valeur);


                        ResponseEntity<String> twilio = restTemplate.exchange(
                                "http://localhost:8093/twilioTriger/"+field.getId()+"/"+data,
                                HttpMethod.GET,
                                new HttpEntity<>("parameters", headers),
                                      String.class);

                        twilio.getBody();


                        ResponseEntity<String> httptriger = restTemplate.exchange(
                                "http://localhost:8093/httpTriger/"+field.getId()+"/"+data,
                                HttpMethod.GET,
                                new HttpEntity<>("parameters", headers),
                                String.class);

                        httptriger.getBody();

                        ResponseEntity<String> emailTriger = restTemplate.exchange(
                                "http://localhost:8093/EmailTriger/"+field.getId()+"/"+data,
                                HttpMethod.GET,
                                new HttpEntity<>("parameters", headers),
                                String.class);

                        emailTriger.getBody();



                    }
                    catch(Exception e) {
                        System.out.println(e.getMessage());
                        return "bad entries";
                    }

                    pusher.trigger("my-channel", "my-event", Collections.singletonMap("data",allParams.get("key")  ));


                }
            }


        }
        return " " + allParams.entrySet();

    }

















}
