package com.example.triggerservice.entities;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class TimeControl {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nom;

    @Temporal(TemporalType.DATE)
    private Date dateAction;
    int hourD;
    int minutesD;

    int hourA;

    int minutesA;

    private   Long idUser;





}
