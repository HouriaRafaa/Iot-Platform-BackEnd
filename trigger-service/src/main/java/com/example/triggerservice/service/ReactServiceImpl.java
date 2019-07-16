package com.example.triggerservice.service;

import com.example.triggerservice.dao.ReactRepository;
import com.example.triggerservice.entities.React;
import com.example.triggerservice.entities.TrigerEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class ReactServiceImpl implements ReactService {

    @Autowired
    ReactRepository reactRepository;



    @Autowired
    NewEmailSenderService newEmailSenderService;

    @Override
    public React saveReact(String nom, String condition, double valeur, Long appUser,int canal, int field) {

        React react = new React();
        react.setNom(nom); react.setCond(condition); react.setVal(valeur);
        react.setAppUser(appUser);
        react.setCanal(canal);
        react.setField(field);
        react.setActivated(true);
        reactRepository.save(react);
        return react;
    }

    @Override
    public void EnvoyerEmail(TrigerEmail trigerEmail) {

        try {
            newEmailSenderService.sendEmail(trigerEmail.getEmail(),"","ESI-IOT REACT",trigerEmail.getMessage());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(trigerEmail.getEmail());
//        mailMessage.setSubject("ESI-IOT REACT!");
//        mailMessage.setFrom("esisba.iot@gmail.com");
//        mailMessage.setText(trigerEmail.getMessage());
//        emailSenderService.sendEmail(mailMessage);

    }
}
