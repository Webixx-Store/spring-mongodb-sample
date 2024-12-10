package com.example.bot_binnance.controller;

import com.example.bot_binnance.common.CommonUtils;
import com.example.bot_binnance.common.JwtUtil;
import com.example.bot_binnance.config.MyUserDetail;
import com.example.bot_binnance.dto.AuthencationRequest;
import com.example.bot_binnance.dto.ResultDto;
import com.example.bot_binnance.model.User;
import com.example.bot_binnance.service.CustomUserDetails;
import com.example.bot_binnance.service.UserService;

import jakarta.validation.Valid;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;





@RestController
@RequestMapping("/api/user/")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired(required = false)
    MyUserDetail UserDetailsService;

    @Autowired(required = false)
    JwtUtil jwtUtilToken;

    @Autowired
    UserService userSerivece;

    @Autowired
    MyUserDetail myUserDetail;


    @PostMapping("save")
    public ResponseEntity<ResultDto<User>> saveOrUpdateUser(@Valid @RequestBody User user) {
        try {
            User savedUser = userService.saveOrUpdateUser(user);
            ResultDto<User> result = new ResultDto<>(200, "Save Member OK", savedUser);
            return CommonUtils.RESULT_OK(result);
        } catch (Exception e) {
        	 ResultDto<User> result = new ResultDto<>(200, "save Fail", null);
        	 return CommonUtils.RESULT_ERROR(result);
        }
    }
    
    @PostMapping("authenticate")
    public ResponseEntity<?> createAuthencationToken(@RequestBody AuthencationRequest authencationRequest)
            throws Exception {
        String userid = "";
        String password = "";
        try {
            if(null==authencationRequest.getGoogleID()) {
                userid = authencationRequest.getUsername();
                password = authencationRequest.getPassword();
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(userid,password));
            }else {
                Optional<User> member = userSerivece.findByToken(authencationRequest.getGoogleID());
                if(member.isPresent()) {
                    userid = member.get().getId();
                }else {
                    throw new BadCredentialsException("google account is not registered");
                }
                
            }
        } catch (Exception e) {
            // TODO: handle exception
        	return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("UNAUTHORIZED " + new Date());
        }
        // get deital userdeil
        final CustomUserDetails userDetails = (CustomUserDetails) UserDetailsService
                .loadUserByUsername(userid);

        // create token
        final String jwt = this.jwtUtilToken.generateToken(userDetails);
        User user = userDetails.getUser();
        user.setJwt(jwt);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping("saveMenu")
    public ResponseEntity<ResultDto<User>> saveMenu(@Valid @RequestBody User user) {
        try {
            User savedUser = userService.saveOrUpdateUser(user);
            ResultDto<User> result = new ResultDto<>(200, "Save Member OK", savedUser);
            return CommonUtils.RESULT_OK(result);
        } catch (Exception e) {
        	 ResultDto<User> result = new ResultDto<>(200, "save Fail", null);
        	 return CommonUtils.RESULT_ERROR(result);
        }
    }

    // Các endpoint khác
}
