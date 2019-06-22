package com.example.triggerservice.web;
import com.example.triggerservice.dao.*;
import com.example.triggerservice.entities.React;
import com.example.triggerservice.entities.TrigerAction;
import com.example.triggerservice.entities.TrigerEmail;
import com.example.triggerservice.entities.TrigerHttp;
import com.example.triggerservice.models.AppUser;
import com.example.triggerservice.models.Canal;
import com.example.triggerservice.models.Field;
import com.example.triggerservice.service.ReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ReactController {

    @Autowired
    private ReactService reactService;




    @Autowired
    ReactRepository reactRepository;



    @Autowired
    TrigerActionRepository trigerActionRepository;

    @Autowired
    TrigerHttpRepository trigerHttpRepository;
    @Autowired
    TrigerRepository trigerRepository;

    @Autowired
    TrigerEmailRepository trigerEmailRepository;

    @Autowired
    RestTemplate restTemplate;




    @RequestMapping(value = "/react")
    public void CreateReact(@RequestBody Map<String, Object> payload, HttpServletRequest request){

        React react;

//        Field field=fieldRepository.findFieldById(Long.parseLong(String.valueOf(payload.get("fieldId"))));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("content-type","application/json");
        ResponseEntity<AppUser> appUser=restTemplate.exchange("http://localhost:8096/appUsers/"+String.valueOf(payload.get("userId")),
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                AppUser.class
        );
        Long userId=appUser.getBody().getId();


        ResponseEntity<Canal> canal=restTemplate.exchange("http://localhost:8092/Allcanals/"+String.valueOf(payload.get("CanalId")),
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                Canal.class
        );

        Long canalId=canal.getBody().getId();


        ResponseEntity<Field> field=restTemplate.exchange("http://localhost:8092/canals/"+String.valueOf(payload.get("CanalId")+"/fields/"+String.valueOf(payload.get("fieldId"))),
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                Field.class
        );
        Long fieldId=field.getBody().getId();
        react=reactService.saveReact(payload.get("nom").toString(),

                payload.get("condition").toString(),
                Double.parseDouble(String.valueOf(payload.get("valeur"))),
                canalId,
                fieldId
                ,userId);
        payload.forEach((s, o) -> {
            if (s.contains("message") && !(payload.get("message").toString().equals("")))
                trigerActionRepository.save(new TrigerAction(null, payload.get("message").toString(), payload.get("tel").toString(), react));


        });

        payload.forEach((s,o)->{
            if(s.contains("commande") && !(payload.get("commande").toString().equals(""))){

             trigerHttpRepository.save(new TrigerHttp(null,payload.get("commande").toString(),trigerRepository.findTrigerById(Long.parseLong(String.valueOf(payload.get("trigerId")))),react));

            }

        });

        payload.forEach((s,o)->{
            if(s.contains("email_react") && !(payload.get("email_react").toString().equals(""))){
                trigerEmailRepository.save(new TrigerEmail(null,payload.get("message_email").toString(),

                        payload.get("email_react").toString(),react
                        ));
            }
        });


        reactRepository.save(react);
    }


    @RequestMapping(value = "/ReactField/{fieldId}")
    public List<React> getReactByFields(@PathVariable Long fieldId, HttpServletRequest request){

        List<React> reacts=new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("content-type","application/json");

        ResponseEntity<Field> field=restTemplate.exchange("http://localhost:8092/fields/"+fieldId,
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                Field.class
        );
        Long fId=field.getBody().getId();
         reacts=reactRepository.findReactByField(fId);
         return reacts;
    }


    @RequestMapping(value = "/UserReact/{userId}",method = RequestMethod.GET)

    public  List<React> getReactUser(@PathVariable Long userId){

        List<React> reacts=reactRepository.findReactByAppUser(userId);
        return reacts;

    }





}
