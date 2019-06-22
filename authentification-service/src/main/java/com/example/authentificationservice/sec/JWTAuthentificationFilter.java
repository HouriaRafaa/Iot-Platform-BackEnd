package com.example.authentificationservice.sec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.example.authentificationservice.entities.AppUser;
import com.example.authentificationservice.service.AccountService;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

   //pour verifier le token

    private AuthenticationManager authenticationManager;

  private AccountService accountService;

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

    }

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager, AccountService accountService) {
        this.authenticationManager = authenticationManager;
        this.accountService = accountService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        AppUser appUser = null;
        try {
            appUser = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);
//
//            System.out.println("etat dutilisateur" +appUser.isActived());
//
//            System.out.println("email "+appUser.getEmail());
                return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getEmail(), appUser.getPassword()));
              } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Pb in request content");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        List<String> roles = new ArrayList<>();
        user.getAuthorities().forEach(a -> {
            roles.add(a.getAuthority());
        });
        //Long id = appUser.getId();

//            System.out.println(accountService.loadUserByUsername(user.getUsername()));

//        System.out.println(user.isEnabled());
        String jwt = JWT.create()
                .withIssuer(request.getRequestURI())
                .withSubject(user.getUsername())
                .withArrayClaim("roles", roles.toArray(new String[roles.size()]))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityParams.EXPIRATION))
                .sign(Algorithm.HMAC256(SecurityParams.SECRET));

//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonJwt = objectMapper.readTree(jwt);
//
//       ((com.fasterxml.jackson.databind.node.ObjectNode) jsonJwt).put("userId",id);


        System.out.println(jwt);

        response.addHeader(SecurityParams.JWT_HEADER_NAME, jwt);


    }
}
