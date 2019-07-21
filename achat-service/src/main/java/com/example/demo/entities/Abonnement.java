package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Abonnement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name = "typeAbonnement")
    private String option ;

    private long credit;

    private int maxChannels ;

    private int maxFields ;

    private long price ;

    @JsonBackReference
    @OneToMany(mappedBy ="abonnement",cascade = CascadeType.ALL)
    private Collection<AppUser> users= new ArrayList<>();

    public Abonnement(String option, long credit, int maxChannels, int maxFields,long price) {
        this.option = option;
        this.credit = credit;
        this.maxChannels = maxChannels;
        this.maxFields = maxFields;
        this.price = price ;
    }
}
