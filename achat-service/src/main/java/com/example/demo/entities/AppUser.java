package com.example.demo.entities;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {


 @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 public AppUser(Long id){
  this.id=id;
 }

 @Column(unique = true)
 private String userName;

 @Column(unique = true)
 private String email;
 
 private Date joinedDate ;

 @JsonManagedReference
 @ManyToOne
 private Abonnement abonnement ;

 public AppUser(String userName) {
  this.userName = userName;
 }

 private long credit;

 public void débiter()
 {
  this.credit--;
 }

 public void ajouterCrédit(long credit )
 {
  Logger log =  LoggerFactory.getLogger(this.getClass());

  log.debug("valeur précédente " + this.getCredit());
  this.credit+=credit ;
  log.debug("nouvelle valeur" + this.getCredit());


 }
 public void débiter(long messagesCount)
 {
  this.credit-=messagesCount ;
 }

 @JsonBackReference
 @OneToMany(mappedBy = "appUser")
 private List<AchatCredit> achats ;

}