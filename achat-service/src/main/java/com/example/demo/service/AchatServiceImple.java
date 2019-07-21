package com.example.demo.service;


import com.example.demo.IStats;
import com.example.demo.Stats;
import com.example.demo.dao.AchatCreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AchatServiceImple implements AchatService {
    @Autowired
    AchatCreditRepository achatCreditRepository;

    @Override
    public ArrayList<Stats> AchatDayStats() {
        ArrayList<Stats> resultList = new ArrayList<>();
        List<IStats> tempList = achatCreditRepository.AchatDayStats();
        tempList.forEach(item -> {
            System.out.println(item.getCount());
            resultList.add(new Stats(item.getDate(), item.getCount()));
        });
        return resultList;
    }



    @Override
    public int getCurrentMonthRevenue() {
        return achatCreditRepository.currentMonthRevenue();
    }
}
