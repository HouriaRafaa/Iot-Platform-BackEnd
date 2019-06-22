package com.example.canalservice.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class ValeurData {


    Long id;

   @CsvBindByName(column = "Value", required = true)
    Double valeur;

    @CsvBindByName(column = "Date", required = true)
    @CsvDate("dd/MM/yyyy")
    Date date;


}
