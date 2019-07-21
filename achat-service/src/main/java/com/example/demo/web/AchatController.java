package com.example.demo.web;

import com.example.demo.Stats;
import com.example.demo.dao.AchatCreditRepository;
import com.example.demo.dao.AppUserRepository;
import com.example.demo.dao.CreditProduitRepository;
import com.example.demo.entities.AchatCredit;
import com.example.demo.entities.AppUser;
import com.example.demo.entities.CreditProduit;
import com.example.demo.service.AchatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class AchatController {

    @Autowired
    AppUserRepository appUserRepository ;

    @Autowired
    AchatService achatService ;

    @Autowired
    CreditProduitRepository creditProduitRepository ;

    @Autowired
    AchatCreditRepository achatCreditRepository ;

    @PostMapping(value = "/appUser/addCredit/{id}")
    public void update(@RequestBody Map<String, Long> payload, @PathVariable Long id) throws Exception {
        AppUser appUser = appUserRepository.findAppUserById(id) ;
        CreditProduit creditProduit = creditProduitRepository.findCreditProduitById(payload.get("idProduit")) ;
        appUser.ajouterCrédit(creditProduit.getCredit());
        achatCreditRepository.save(new AchatCredit(null,appUser,creditProduit,new Date()));
    }

//    @GetMapping(value = "/achatCountByMonth")
//    @ResponseBody
//    public ArrayList<Stats> GetAchatStatsByMonth() {
//        ArrayList<Stats> reponse = new ArrayList<>();
//        reponse = achatService.AchatDayStats();
//        return reponse;
//    }

    @GetMapping(value = "/achatCountByDay")
    @ResponseBody
    public ArrayList<Stats> GetAchatStatsByDay() {
        ArrayList<Stats> reponse = new ArrayList<>();
        reponse = achatService.AchatDayStats();
        return reponse;
    }

//    @GetMapping(value = "/achatCountByYear")
//    @ResponseBody
//    public ArrayList<Stats> GetAchatStatsByYear() {
//        ArrayList<Stats> reponse = new ArrayList<>();
//        reponse = achatService.AchatMonthStats();
//        return reponse;
//    }

    @GetMapping(value = "/currentMonthRevenue")
    public int currentMonthRevenue(){
        return achatService.getCurrentMonthRevenue();
    }

    @GetMapping(value="/getUserAchat/{id}")
    public List<AchatCredit> getUserAchat(@PathVariable Long id )
    {
        AppUser appUser = appUserRepository.findAppUserById(id) ;
        return achatCreditRepository.findAllByAppUser(appUser) ;
    }

    @PostMapping(value = "/appUsers/getId")
    public Long getId(@RequestBody Map<String, String> payload )
    {
        System.out.println(payload.get("email"));
        System.out.println(appUserRepository.findByEmailIgnoreCase(payload.get("email")).getId() );
        return appUserRepository.findByEmailIgnoreCase(payload.get("email")).getId() ;
    }

    @GetMapping(value="/appUsers/getId/{email}")
    public Long getId(@PathVariable String email)
    {
        return appUserRepository.findByEmailIgnoreCase(email).getId() ;
    }

}
