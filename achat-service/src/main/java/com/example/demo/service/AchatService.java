package com.example.demo.service;



import com.example.demo.Stats;

import java.util.ArrayList;

public interface AchatService {
    public ArrayList<Stats> AchatDayStats() ;

    public int getCurrentMonthRevenue();
}
