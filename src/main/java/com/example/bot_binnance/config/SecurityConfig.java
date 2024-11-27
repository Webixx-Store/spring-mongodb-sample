package com.example.bot_binnance.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.bot_binnance.Filter.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetail userDetail;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(MyUserDetail userDetail, JwtRequestFilter jwtRequestFilter) {
        this.userDetail = userDetail;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @SuppressWarnings("deprecation")
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and()
            .csrf().disable()
            .authorizeRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/user/authenticate" 
                    		, "/api/user/save" 
                    		, "/api/binance/**" 
                    		, "/api/products"
                    		, "/api/products/**"
                    		, "/api/products/upload" 
                    		,"/upload/product/**"
                    		,"/ws/**")
                    .permitAll()
                    .anyRequest().authenticated()
            )
            .sessionManagement(sessionManagement ->
                sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
   	public BCryptPasswordEncoder passwordEncoder () {
       	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
   		return bCryptPasswordEncoder; 
   	}
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetail).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}

