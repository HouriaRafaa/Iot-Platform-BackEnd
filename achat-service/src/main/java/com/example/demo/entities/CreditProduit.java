package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditProduit {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id ;
        private int credit ;
        private int price ;

        public CreditProduit(int credit, int price) {
                this.credit = credit;
                this.price = price;
        }

        @JsonBackReference
        @OneToMany(mappedBy = "produit")
        private List<AchatCredit> achats ;
}
