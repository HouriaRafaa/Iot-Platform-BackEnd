package com.example.triggerservice.web;

import com.example.triggerservice.dao.CommandeRepository;
import com.example.triggerservice.dao.TimeControlActionRepository;
import com.example.triggerservice.dao.TimeControlRepository;
import com.example.triggerservice.dao.TrigerRepository;
import com.example.triggerservice.entities.Commande;
import com.example.triggerservice.entities.TimeControl;
import com.example.triggerservice.entities.TimeControlAction;
import com.example.triggerservice.entities.Triger;
import com.example.triggerservice.models.AppUser;
import com.example.triggerservice.service.TriggerService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    TimeControlRepository timeControlRepository;


    @Autowired
    TimeControlActionRepository timeControlActionRepository;

    int qos = 1;
    String broker = "tcp://localhost:1883";


    public MqttClient mqttConnect() throws MqttException {

        String clientId = UUID.randomUUID().toString();
        MemoryPersistence persistence = new MemoryPersistence();
        System.out.println("About to connect to MQTT broker  tcp://localhost:1883");
        MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(false);
        sampleClient.connect(connOpts);
        System.out.println("Connected");
        return sampleClient;
    }

    @RequestMapping(value = "/trigger")

    public void addTrigger(@RequestBody Map<String, Object> payload, HttpServletRequest request) {

        Triger triger;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("content-type", "application/json");

        ResponseEntity<AppUser> responseEntity = restTemplate.exchange("http://authentification-service/appUsers/" + String.valueOf(payload.get("userId")),
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                AppUser.class
        );

        Long userId = responseEntity.getBody().getId();


        triger = triggerService.saveTrigger(
                payload.get("nom").toString(),
                userId,
                null);

        trigerRepository.save(triger);

        payload.forEach((s, o) -> {
            if (s.contains("commande")) {

                commandeRepository.save(new Commande(null, o.toString(), false, triger));
            }
        });

    }

    @RequestMapping(method = RequestMethod.POST, value = "/trigers/{trigerId}")
    public void updateTrigger(@RequestBody Map<String, Object> payload, @PathVariable Long trigerId) {

        Triger triger = trigerRepository.findById(trigerId).get();
        if (triger != null) {

            payload.forEach((s, o) -> {
                if (s.contains("commande")) {
                    commandeRepository.save(new Commande(null, o.toString(), false, triger));

                }
            });
        }


    }

    @RequestMapping(method = RequestMethod.GET, value = "/trigers/{trigerId}")

    public Triger getTriger(@PathVariable Long trigerId) {

        Triger t = trigerRepository.findTrigerById(trigerId);
        return t;


    }


    @RequestMapping(value = "/commandes/{id}", method = RequestMethod.GET)
    public Collection<Commande> getCmd(@PathVariable Long id) {

        Triger trigers = trigerRepository.findTrigerById(id);

        return trigers.getCommandes();


    }

    @RequestMapping(value = "/ExecuteCommands/{id}", method = RequestMethod.GET)
    public String executeCommande(@PathVariable Long id) {
        Triger triger = trigerRepository.findById(id).get();



        Commande c = new Commande();
        if (triger != null && triger.getCommandes().size() != 0) {
            for (Commande commande : triger.getCommandes()) {

                if (!commande.isExecuted()) {
                    commande.setExecuted(true);
                    commandeRepository.save(commande);
                    c = commande;
                }

            }

        }

//        try {
//            MqttClient mqttClient = mqttConnect();
//            MqttMessage mqttMessage = new MqttMessage(c.getValeur().getBytes());
//            mqttMessage.setQos(qos);
//            mqttClient.publish("IOT", mqttMessage);
//            System.out.println("Message published");
//        } catch (Exception e) {
//
//        }

        return c.getValeur();

    }


    @RequestMapping(value = "/userTriger/{id}", method = RequestMethod.GET)
    public List<Triger> getTrigerUser(@PathVariable Long id) {

        List<Triger> trigers = trigerRepository.findTrigerByUser(id);

        return trigers;

    }


    @RequestMapping(value = "/trigers/{id}/commandes")

    public List<Commande> findcommands(@PathVariable Long id){

        Triger triger= trigerRepository.findTrigerById(id);
         List<Commande> commandes=commandeRepository.findCommandeByTriger(triger);

         return commandes;
    }





    @RequestMapping(value = "/timeControl", method = RequestMethod.POST)

    public void addTimeControl(@RequestBody TimeControlForm timeControlForm, HttpServletRequest request) {
        TimeControl timeControl;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        headers.add("content-type", "application/json");

        ResponseEntity<AppUser> responseEntity = restTemplate.exchange("http://authentification-service/appUsers/" + String.valueOf(timeControlForm.getIdUser()),
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                AppUser.class
        );

        Long userId = responseEntity.getBody().getId();

       timeControl= triggerService.saveTimeControl(timeControlForm.getNom(),
                timeControlForm.getDateControl(),
              Integer.parseInt(timeControlForm.getTimeHour()),
             Integer.parseInt(timeControlForm.getMinutesHour())
               ,
                userId
        );

        timeControlActionRepository.save(new TimeControlAction(null, timeControlForm.getCommande(),
                timeControl
        ));
    }

    @RequestMapping(value = "/ExceuteTimeControl/{id}")
    public  String executeTimeConntrol (@PathVariable Long id){
        TimeControl timeControl= timeControlRepository.findById(id).get();
        TimeControlAction timeControlAction=timeControlActionRepository.findTimeControlActionByTimeControl(timeControl);
        Date date=new Date();
        Calendar calendar= Calendar.getInstance(TimeZone.getTimeZone("Africa/Algiers"));
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day =calendar.get(Calendar.DAY_OF_MONTH);
        int minutes = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR);
        Date dateControl = timeControl.getDateAction();
        int min = timeControl.getMinutes();
        int  h = timeControl.getHour();
        Calendar calendar1 = Calendar.getInstance(TimeZone.getTimeZone("Africa/Algiers"));
        calendar1.setTime(dateControl);

        int yearControl = calendar.get(Calendar.YEAR);
        int monthControl = calendar.get(Calendar.MONTH);
        int dayControl = calendar.get(Calendar.DAY_OF_MONTH);


        if(year==yearControl & month==monthControl & day==dayControl & minutes==min & hour==h){

                return  timeControlAction.getCommande();

        }
      else  return "pas de commande :( :)";
    }


}

@Data
@AllArgsConstructor
@NoArgsConstructor
class TimeControlForm {

    private String nom;

    private Date dateControl;

    private String timeHour;

    private String  minutesHour;

    private Long idUser;

    private String commande;

    private Long idTriger;
}