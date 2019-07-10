package com.example.authentificationservice.service;


import com.example.authentificationservice.entities.AppRole;
import com.example.authentificationservice.entities.AppUser;
import org.apache.http.HttpResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AccountService
{


    public AppUser saveUser(String username, String email, String password, String confirmedPassword);

    public AppRole saveRole(AppRole role);

    public AppUser loadUserByUsername(String username);
    public AppUser loadUserByEmail(String email);

    public AppUser loadUserById(Long id);

    public  void addRoleToUser(String username, String rolename);



}
