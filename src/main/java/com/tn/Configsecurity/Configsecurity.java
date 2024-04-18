package com.tn.Configsecurity;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Configsecurity {

    @Bean

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user5 = User
                .withUsername("t5")
                .password(passwordEncoder().encode("1234567"))
                .build();

        return new InMemoryUserDetailsManager(user5);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf().disable();

        // pass không cần usernam paswword

        httpSecurity.authorizeRequests().requestMatchers("/register").permitAll();
        // bất kỳ request nào cũng phải đăng nhập
        httpSecurity.authorizeRequests().anyRequest().authenticated();

        httpSecurity.httpBasic();
        return httpSecurity.build();
    }
}
