package com.example.bot_binnance.common;

import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import com.example.bot_binnance.dto.ResultDto;

public class CommonUtils {
	
	 public static <T> ResponseEntity<ResultDto<T>> RESULT_ERROR(ResultDto<T> result) {
	        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	 }
	
	public static <T> ResponseEntity<ResultDto<T>> RESULT_OK(ResultDto<T> result){
	    return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	public static  HttpStatus determineHttpStatus(Exception e) {
	    if (e instanceof ConfigDataResourceNotFoundException) {
	        return HttpStatus.NOT_FOUND; // 404
	    } else if (e instanceof IllegalArgumentException) {
	        return HttpStatus.BAD_REQUEST; // 400
	    } else if (e instanceof AccessDeniedException) {
	        return HttpStatus.FORBIDDEN; // 403
	    } else if (e instanceof AuthenticationException) {
	        return HttpStatus.UNAUTHORIZED; // 401
	    } else {
	        return HttpStatus.INTERNAL_SERVER_ERROR; // 500
	    }
	}


}
