package com.example.demo.service;

import com.example.demo.Stats;
import com.example.demo.entities.AppUser;

import java.util.ArrayList;

public interface AccountService
{
    public AppUser saveUser(String username, String email, String option);
    public AppUser loadUserByUsername(String username);
    public AppUser loadUserById(Long id);

    public ArrayList<Stats> userMonthStats() ;




}
