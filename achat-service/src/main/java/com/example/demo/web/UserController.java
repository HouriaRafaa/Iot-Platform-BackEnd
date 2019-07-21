package com.example.demo.web;

import com.example.demo.Stats;
import com.example.demo.dao.AppUserRepository;
import com.example.demo.entities.AppUser;
import com.example.demo.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@RestController
public class UserController {

    @Autowired
    AccountService accountService ;

    @Autowired
    AppUserRepository appUserRepository ;
    @PostMapping(value = "/addUser")
    public AppUser addUser(@RequestBody UserForm userForm, HttpServletRequest request )
    {
        System.out.println(userForm.getEmail());
            accountService.saveUser(userForm.getUsername(),userForm.getEmail(),userForm.getOpt());
        return null;
    }

    @GetMapping(value="/debiter/{id}")
    public AppUser debiterCredit(@PathVariable Long id)
    {
        AppUser appUser = accountService.loadUserById(id);
        System.out.println("my credit " + appUser.getCredit()) ;
        appUser.débiter();
        System.out.println("my credit after debit()" + appUser.getCredit()) ;
        appUserRepository.save(appUser);
                return appUser ;
    }

    @GetMapping(value = "/getUsersCount")
    public long getUsersCount()
    {
        return appUserRepository.count() ;
    }

    @GetMapping(value = "/userMonthStats")
    private ArrayList<Stats> userMonthStats()
    {
        return accountService.userMonthStats() ;
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserForm{
    @Size(min = 7, max = 10)
    private String username;
    private String email;
    private  String password;
    private String passwordConfirmed;
    private String newPassword;
    private String opt ;

}
