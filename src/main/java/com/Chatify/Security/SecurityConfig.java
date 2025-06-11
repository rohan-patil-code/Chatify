package com.Chatify.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Chatify.Service.Myappservicee;

import lombok.Builder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
        @Autowired
        private  Myappservicee myappservicee;

        @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(jwtUtill(), userDetailsService());
    }

@Bean
public JwtUtill jwtUtill() {
    return new JwtUtill();
}


        @Bean
        public UserDetailsService userDetailsService(){
            return myappservicee;
        }

        @Bean
        public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }


        @Bean
        public AuthenticationProvider authenticationProvider(){

            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

            provider.setUserDetailsService(myappservicee);

            provider.setPasswordEncoder(passwordEncoder());

            return provider;
        }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpsecurity)throws Exception{

        // return httpsecurity
        // .csrf(AbstractHttpConfigurer::disable)
        // .formLogin(httpForm ->{
        //     httpForm 
        //     .loginPage("/login")
        //     .defaultSuccessUrl("/home",true)
        //     .permitAll();
        // })
        // .authorizeHttpRequests(registry ->{
        //     registry.requestMatchers("/req/signup", "/css/**","/js/**").permitAll();
        //     registry.anyRequest().authenticated();
        // })

        // .build(); 

        return httpsecurity
            .csrf(csrf -> csrf.disable())
            .authorizeRequests(auth -> auth

                .requestMatchers("/api/auth/signup","/api/auth/login","/api/auth/refresh-token","/api/auth/logout","/api/chats","/css/**","/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{

        return http.getSharedObject(AuthenticationManagerBuilder.class)
        .authenticationProvider(authenticationProvider())
        .build();
    }
}
