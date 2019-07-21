package com.example.canalservice.web;

import com.example.canalservice.dao.FieldRepository;
import com.example.canalservice.dao.ValeurRepository;
import com.example.canalservice.entities.Canal;
import com.example.canalservice.entities.Field;
import com.example.canalservice.entities.Valeur;
import com.example.canalservice.models.ValeurData;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class DataCSVController {

    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    ValeurRepository valeurRepository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/export-data/{id}")
    public void exportCSV(HttpServletResponse response , @PathVariable int id) throws Exception {

        List<Valeur> list=null;
        List<ValeurData> valeur = new ArrayList<ValeurData>();
        Field field = fieldRepository.findFieldByFieldId(id);

        list = new ArrayList(field.getValeur());

        for(Valeur v:list){
            ValeurData data  = new ValeurData(v.getId(),v.getValeur(),v.getDate());
            valeur.add(data);
        }

        String filename = "data.csv";
        response.setContentType("text/csv");
        response.setHeader(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        StatefulBeanToCsv<ValeurData> writer = new StatefulBeanToCsvBuilder<ValeurData>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(true)
                .build();

        writer.write(valeur);


    }


    @PostMapping("/import-data/{id}")
    @ResponseBody
    public void importCSV(@RequestParam("file") MultipartFile file, HttpServletRequest request , @PathVariable int id) {

        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = null;
        Field field = fieldRepository.findFieldByFieldId(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", request.getHeader("Authorization"));
        System.out.println(request.getHeader("Authorization"));
        headers.add("Content-Type","application/json");


        if (field!= null){
            String nameOfField = field.getNom();
            Canal canal = field.getCanal();
            String writeKey = canal.getCleEcriture();
            String serviceURL = "http://canal-service/record?key="+writeKey+"&"+ nameOfField +"=";
            try {
                fos = new FileOutputStream( convFile );
                fos.write( file.getBytes() );
                fos.close();
                Reader reader = Files.newBufferedReader(convFile.toPath());
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
                String[] nextRecord;
                while ((nextRecord = csvReader.readNext()) != null) {
                    //   Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(nextRecord[0]);

                    //  System.out.println(serviceURL+nextRecord[1]);
                    ResponseEntity<String> csv = restTemplate.exchange(
                            serviceURL+nextRecord[1],
                            HttpMethod.GET,
                            new HttpEntity<>("parameters", headers),
                            String.class);

                    csv.getBody();


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}


