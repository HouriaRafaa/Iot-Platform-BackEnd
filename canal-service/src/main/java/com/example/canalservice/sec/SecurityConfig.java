package com.example.canalservice.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }
    //Pour les Api_key dans le navigateur


    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
        web.httpFirewall(defaultHttpFirewall());
        web.ignoring().antMatchers("http://192.168.8.103:8091/ExecuteCommands/**",
                        "/ExecuteCommands/**","/api/v1/sms")
                .antMatchers(HttpMethod.GET,"/update-password/**");}
    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http.csrf().disable();
         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         http.authorizeRequests().antMatchers("/login/**","/register/**","/record/**","/confirm-account/**",
                 "/send-email",
                 " 192.168.43.11/record/**"
                 ,"/password-confirmation/**"
                 ,"/export-data/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/update-password/**");
        http.authorizeRequests().antMatchers("/appUsers/**","/appRoles/**").hasAuthority("ADMIN");


       //  http.authorizeRequests().antMatchers("/canals");
 //       http.authorizeRequests().antMatchers("/fields/**").permitAll();


//        http
//                .authorizeRequests()
//                .antMatchers( "/appUsers/{id}/canals").authenticated();

         http.addFilterBefore(new JWTAutorisationFilter(),UsernamePasswordAuthenticationFilter.class);

        }

}
