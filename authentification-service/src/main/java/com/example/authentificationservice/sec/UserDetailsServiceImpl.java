package com.example.authentificationservice.sec;

import com.example.authentificationservice.entities.AppUser;
import com.example.authentificationservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException ,RuntimeException{
        AppUser appUser=accountService.loadUserByEmail(email);
        if(appUser==null)throw new UsernameNotFoundException("Invalid user");

//        if(!appUser.isActived()){
//            throw  new RuntimeException("Le compte n'est pas activé");
//        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });

        return new User(appUser.getEmail(),appUser.getPassword(),authorities);
    }
}
