package com.example.bot_binnance.config;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.example.bot_binnance.model.User;
import com.example.bot_binnance.repository.UserRepository;
import com.example.bot_binnance.service.CustomUserDetails;
import com.example.bot_binnance.service.UserService;

import io.micrometer.common.util.StringUtils;


@Service
public class MyUserDetail  implements UserDetailsService  {
	  @Autowired
	  UserRepository dao;
	  
	  @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		  
		    
	        User  user = this.dao.findByEmailOrId(email , email).get();
	           
	        if (user == null) {
	            throw new UsernameNotFoundException("Khong co tai khoan");
	        }
	        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	        if (StringUtils.isNotEmpty(user.getRole())) {
	        	authorities.add(new SimpleGrantedAuthority(user.getRole()));
	        }else {
	        	authorities.add(new SimpleGrantedAuthority("guess"));
	        }
	        
	        // System.out.println("this is authorite" + authorities.toString() +
	        //         "userId : " + user.getUserId());
	        // config thêm các thông tin cần vào trong userDetail
	        return new CustomUserDetails(user.getPassword(), user.getId(), true, true, true, true, authorities, user);
	    }

}
