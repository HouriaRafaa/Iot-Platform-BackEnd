package com.example.demo.service;



import com.example.demo.IStats;
import com.example.demo.Stats;
import com.example.demo.dao.AbonnementRepository;
import com.example.demo.dao.AppUserRepository;
import com.example.demo.entities.Abonnement;
import com.example.demo.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    AbonnementRepository abonnementRepository ;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public AppUser saveUser(String username, String email,String option ) {
        AppUser appUser = new AppUser();
        Abonnement abonnement= abonnementRepository.findAbonnementByOption(option) ;
        appUser.setUserName(username);
        appUser.setEmail(email);

        appUser.setAbonnement(abonnement);
        appUser.setJoinedDate(new Date());
        appUser.setCredit(abonnement.getCredit());
        appUserRepository.save(appUser);
        return appUser;
    }



    @Override
    public AppUser loadUserByUsername(String username)
    {
        AppUser user =appUserRepository.findByUserName(username);
        if(user==null)
            throw new RuntimeException("User n existe psa");
        return user;
    }




    @Override
    public AppUser loadUserById(Long id) {
        return appUserRepository.findById(id).get();
    }

    @Override
    public ArrayList<Stats> userMonthStats() {
        ArrayList<Stats> resultList = new ArrayList<>();
        List<IStats> tempList = appUserRepository.UserStatsMonth();
        tempList.forEach(item -> {
            System.out.println(item.getCount());
            resultList.add(new Stats(item.getDate(), item.getCount()));
        });
        return resultList;
    }

}
