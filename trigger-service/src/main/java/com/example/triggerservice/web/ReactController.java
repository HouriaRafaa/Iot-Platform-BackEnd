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
import sun.misc.Request;

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
        ResponseEntity<AppUser> appUser=restTemplate.exchange("http://authentification-service/appUsers/"+String.valueOf(payload.get("userId")),
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                AppUser.class
        );
        Long userId=appUser.getBody().getId();


        ResponseEntity<Canal> canal=restTemplate.exchange("http://canal-service/Allcanals/"+payload.get("CanalId"),
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                Canal.class
        );


        int canalId=canal.getBody().getCanalId();


        System.out.println("hhhhhhhhhhhhhhhhhhhh "+canalId);


        ResponseEntity<Field> field=restTemplate.exchange("http://canal-service/canals/"+payload.get("CanalId")+"/field/"+payload.get("field"),
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                Field.class
        );
        int fieldId=field.getBody().getFieldId();

        System.out.println("hhhhhhhhhhhh bbbbbbbb "+fieldId);


        react=reactService.saveReact(payload.get("nom").toString(),
                payload.get("condition").toString(),
                Double.parseDouble(String.valueOf(payload.get("valeur"))),
                userId,
               canalId,
                fieldId
        );

            if (!(payload.get("message").toString().equals(""))) {
                trigerActionRepository.save(new TrigerAction(null, payload.get("message").toString(), payload.get("tel").toString(), react));
            }



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
    public List<React> getReactByFields(@PathVariable int fieldId, HttpServletRequest request){

        List<React> reacts=new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("content-type","application/json");

        ResponseEntity<Field> field=restTemplate.exchange("http://canal-service/fields/"+fieldId,
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                Field.class
        );
        int fId=field.getBody().getFieldId();
         reacts=reactRepository.findReactByField(fId);
         return reacts;
    }




    @RequestMapping(value = "/UserReact/{userId}",method = RequestMethod.GET)


    public  List<React> getReactUser(@PathVariable Long userId)  {

        List<React> reacts=reactRepository.findReactByAppUser(userId);
        return reacts;

    }




    @RequestMapping(value = "/DesactivateReact/{reactId}",method = RequestMethod.GET)
    public String  desactiverReact(@PathVariable Long reactId)   {
        React react= reactRepository.findReactById(reactId);
        if(react.isActivated())
            react.setActivated(false);
        reactRepository.save(react);

        return "React desactive messkin :( :(  ";
    }

    @RequestMapping(value = "/ActivateReact/{reactId}",method = RequestMethod.GET)

    public String  activerReact(@PathVariable Long reactId){

        React react= reactRepository.findReactById(reactId);
        if(!react.isActivated())
            react.setActivated(true);
        reactRepository.save(react);

        return "React active  حورية :) :) الحمد لله   ";
    }





}
