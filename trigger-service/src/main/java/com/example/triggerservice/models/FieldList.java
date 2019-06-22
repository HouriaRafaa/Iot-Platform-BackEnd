package com.example.triggerservice.models;


import com.example.triggerservice.entities.React;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
public class FieldList {


    private List<React>fields=new ArrayList<>();
}
