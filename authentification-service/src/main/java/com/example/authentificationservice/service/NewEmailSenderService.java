package com.example.authentificationservice.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.ImageType;
import it.ozimov.springboot.mail.model.InlinePicture;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultInlinePicture;
import it.ozimov.springboot.mail.service.EmailService;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

@Service
public class NewEmailSenderService {

    @Autowired
    private EmailService emailService;

    public void sendEmail(String address,String name , String subject,String body) throws UnsupportedEncodingException {
        final Email email = DefaultEmail.builder()
                .from(new InternetAddress("esisba.iot@gmail.com", "ESI-IOT-PLATFORM"))
                .to(newArrayList(new InternetAddress(address, name)))
                .subject(subject)
                .body("Hello " + name +",\n\n" + body + "\n\n\n----------------\n\n" +
                        "ESI-IOT-PLATFORM\n" +
                        "an iot platform created by esi sba group develper\n" +
                        "All write are reserved.")
                .encoding("UTF-8").build();
        emailService.send(email);
    }

}