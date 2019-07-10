package com.example.authentificationservice.service;


import com.example.authentificationservice.dao.AppRoleRepository;
import com.example.authentificationservice.dao.AppUserRepository;
import com.example.authentificationservice.dao.ConfirmationTokenRepository;
import com.example.authentificationservice.entities.AppRole;
import com.example.authentificationservice.entities.AppUser;
import com.example.authentificationservice.entities.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {


    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public AppUser saveUser(String username, String email, String password, String confirmedPassword) {

        AppUser user = appUserRepository.findByUserName(username);
        if(user!=null)
            throw new RuntimeException("User already exists");

        if(!password.equals(confirmedPassword)) throw  new RuntimeException("Please Confirm your password");
        AppUser appUser = new AppUser();
        appUser.setUserName(username);
       // appUser.setActived(false);
        appUser.setPassword(bCryptPasswordEncoder.encode(password));
        appUser.setEmail(email);
        appUserRepository.save(appUser);
        addRoleToUser(username,"USER");

        ConfirmationToken confirmationToken = new ConfirmationToken(appUser);

        confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(appUser.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("esisba.iot@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
            emailSenderService.sendEmail(mailMessage);


        return appUser;
    }

    @Override
    public AppRole saveRole(AppRole role) {



        return appRoleRepository.save(role);
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
    public AppUser loadUserByEmail(String email) {

        AppUser user=appUserRepository.findByEmailIgnoreCase(email);

        if(user==null) throw  new RuntimeException("user makench");
        return user;
    }


    @Override
    public AppUser loadUserById(Long id) {
        return appUserRepository.findById(id).get();
    }


    @Override
    public void addRoleToUser(String username, String rolename) {
        AppUser appUser = appUserRepository.findByUserName(username);
        System.out.println(" user :..."+ appUser);
        AppRole appRole = appRoleRepository.findByRoleName(rolename);
        System.out.println(" roooel :..."+appRole);
        appUser.getRoles().add(appRole);

    }






}
