package com.example.canalservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customSequences") @Data @NoArgsConstructor @AllArgsConstructor
public class CustomSequences {
    @Id
    private String id;
    private int seq;

// getters and setters
}