package com.example.authentificationservice.web;


import com.example.authentificationservice.dao.AppUserRepository;
import com.example.authentificationservice.dao.ConfirmationTokenRepository;
import com.example.authentificationservice.entities.AppUser;
import com.example.authentificationservice.entities.ConfirmationToken;
import com.example.authentificationservice.service.AccountService;

import com.example.authentificationservice.service.NewEmailSenderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private AppUserRepository userRepository;



    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private NewEmailSenderService newEmailSenderService;

    @PostMapping("/register")
    public AppUser register(@RequestBody @Valid UserForm userForm) throws RuntimeException
    {
        AppUser user =userRepository.findByEmailIgnoreCase(userForm.getEmail());
//        System.out.println("aaaaaa" + user.getEmail());

        if (user!= null) {
            throw new RuntimeException("Invalid user");
        }
        return accountService.saveUser(userForm.getUsername(),userForm.getEmail(),userForm.getPassword(),userForm.getPasswordConfirmed());
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            AppUser user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setActived(true);
            userRepository.save(user);

        }

        return "Compte Verifié ^__^";
    }


    @RequestMapping(value = "/getUserState",method = RequestMethod.GET)

    public boolean getState(Authentication authentication){
        AppUser appUser=accountService.loadUserByEmail(authentication.getName());
        return  appUser.isActived();
    }

    @RequestMapping (value = "/send-email",method = RequestMethod.POST)
    public String resetPasssowrd(@RequestBody UserForm userForm ){
        AppUser appUser=userRepository.findByEmailIgnoreCase(userForm.getEmail());

        if(appUser==null) throw  new RuntimeException("User Not Found");

        if(appUser !=null){

            ConfirmationToken confirmationToken= new ConfirmationToken(appUser);
            confirmationTokenRepository.save(confirmationToken);
//
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setTo(appUser.getEmail());
//            mailMessage.setSubject("Reset Your Password!");
//            mailMessage.setFrom("esisba.iot@gmail.com");
//            mailMessage.setText("To reset your password, please click here : "
//                    + "http://localhost:8080/update-password?token=" + confirmationToken.getConfirmationToken());
//            emailSenderService.sendEmail(mailMessage);


            // sending verification email

            try {
                newEmailSenderService.sendEmail(appUser.getEmail(),appUser.getUserName(),"Reset Your Password!","To reset your password, please click here : "
                        + "http://localhost:8080/reset-password?token=" + confirmationToken.getConfirmationToken());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return  "Message Envoyé";
    }

//    @RequestMapping(value = "password-confirmation",method = RequestMethod.GET)
//
//    public String getPasswordConfirmation(@RequestParam("token")String confirmationToken) {
//
//        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
//        if (token != null) {
//            return "Lien visité";
//        }
//        return null;
//    }


    @RequestMapping(value = "reset-password",method= {RequestMethod.GET, RequestMethod.POST})
    public String updatePassword(@RequestParam("token")String confirmationToken,@RequestBody UserForm userForm){
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token != null)
        {
            AppUser user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setPassword(bCryptPasswordEncoder.encode(userForm.getNewPassword()));
            userRepository.save(user);
        }
        return "Password Changed";
    }




    @RequestMapping(value = "/id", method = RequestMethod.GET)
    @ResponseBody
    public Long currentUserName(Authentication authentication) {


        AppUser user= accountService.loadUserByEmail(authentication.getName());
        return user.getId();
    }

    @RequestMapping(value = "/appUsers/{userId}")
    public AppUser getAppUser(@PathVariable Long userId){

        AppUser appUser=appUserRepository.findById(userId).get();

        return appUser;



    }



}

@Data @NoArgsConstructor @AllArgsConstructor
class UserForm{
    @Size(min = 5, max = 10)
    private String username;
    private String email;
    private  String password;
    private String passwordConfirmed;

    private String newPassword;

}


