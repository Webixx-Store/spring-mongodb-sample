package com.example.bot_binnance.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.bot_binnance.dto.ResultDto;

public class CommonUtils {
	
	 public static <T> ResponseEntity<ResultDto<T>> RESULT_ERROR(ResultDto<T> result) {
	        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
	 }
	
	public static <T> ResponseEntity<ResultDto<T>> RESULT_OK(ResultDto<T> result){
	    return new ResponseEntity<>(result, HttpStatus.OK);
	}


}
