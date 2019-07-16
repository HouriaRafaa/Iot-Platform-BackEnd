package com.example.triggerservice.web;


import com.example.triggerservice.dao.*;
import com.example.triggerservice.entities.*;
import com.example.triggerservice.service.ReactService;
import com.example.triggerservice.twilio.Service;
import com.example.triggerservice.twilio.SmsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ReactCanalController {

    @Autowired
    ReactRepository reactRepository;

    @Autowired
    TrigerActionRepository trigerActionRepository;


    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    TrigerHttpRepository trigerHttpRepository;

    @Autowired
    TrigerEmailRepository trigerEmailRepository;


    @Autowired
    ReactService reactService;

    private final Service service;

    @Autowired
    public ReactCanalController(Service service) {
        this.service = service;
    }

    @Autowired
    RestTemplate restTemplate;

    @GetMapping (value = "/twilioTriger/{fieldId}/{data}")

    public String envoyerTwilio(@PathVariable int fieldId,@PathVariable double data ,HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("content-type", "application/json");

        ResponseEntity<List<React>> response = restTemplate.exchange(
                "http://trigger-service/ReactField/" + fieldId,
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                new ParameterizedTypeReference<List<React>>() {
                });
        List<React> myReacts = response.getBody();

        //           System.out.println("List React "+myReacts);

        for (React react : myReacts) {

            if (react.isActivated()) {
                TrigerAction trigerAction = trigerActionRepository.findTrigerActionByReact(react);
                if (trigerAction != null) {
                    if (react.getCond().equals("is greater than") && data > react.getVal()) {
                        service.sendSms(new SmsRequest(trigerAction.getTel(), trigerAction.getMessage()));
                    } else if (react.getCond().equals("is less than") && data < react.getVal()) {
                        service.sendSms(new SmsRequest(trigerAction.getTel(), trigerAction.getMessage()));
                    } else if (react.getCond().equals("is equal to") && data == react.getVal()) {
                        service.sendSms(new SmsRequest(trigerAction.getTel(), trigerAction.getMessage()));
                    }
                }
            }
            }
            return "Twilio Envoyé";
        }



    @GetMapping(value = "httpTriger/{fieldId}/{data}")
    public  String envoyerHttp(@PathVariable int fieldId,@PathVariable double data ,HttpServletRequest request){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("content-type","application/json");

        ResponseEntity<List<React>> response = restTemplate.exchange(
                "http://trigger-service/ReactField/"+fieldId,
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                new ParameterizedTypeReference<List<React>>(){});
        List<React> myReacts = response.getBody();

        for(React react:myReacts) {
            if (react.isActivated()) {
                TrigerHttp trigerHttp = trigerHttpRepository.findTrigerHttpByReact(react);
                if (trigerHttp != null) {
                    if (react.getCond().equals("is greater than") && data > react.getVal()) {

                        commandeRepository.save(new Commande(null, trigerHttp.getCommande(), false, trigerHttp.getTriger()));

                    } else if (react.getCond().equals("is less than") && data < react.getVal()) {
                        commandeRepository.save(new Commande(null, trigerHttp.getCommande(), false, trigerHttp.getTriger()));
                    } else if (react.getCond().equals("is equal to") && data == react.getVal()) {
                        commandeRepository.save(new Commande(null, trigerHttp.getCommande(), false, trigerHttp.getTriger()));
                    }
                }
            }
        }
       return  "Http Envoyé";
    }



    @GetMapping(value = "/EmailTriger/{fieldId}/{data}")
    public String envoyerEmailTriger(@PathVariable int fieldId,@PathVariable double data ,HttpServletRequest request){

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("content-type","application/json");

        ResponseEntity<List<React>> response = restTemplate.exchange(
                "http://trigger-service/ReactField/"+fieldId,
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                new ParameterizedTypeReference<List<React>>(){});
        List<React> myReacts = response.getBody();



        for(React react:myReacts) {

            if (react.isActivated()) {
                TrigerEmail trigerEmail = trigerEmailRepository.findTrigerEmailByReact(react);

                if (trigerEmail != null) {
                    if (react.getCond().equals("is greater than") && data > react.getVal()) {


                        reactService.EnvoyerEmail(trigerEmail);

                    } else if (react.getCond().equals("is less than") && data < react.getVal()) {

                        reactService.EnvoyerEmail(trigerEmail);
                    } else if (react.getCond().equals("is equal to") && data == react.getVal()) {
                        reactService.EnvoyerEmail(trigerEmail);

                    }

                }

            }
        }


        return "Email Envoyé";
    }














}
