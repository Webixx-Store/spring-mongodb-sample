package com.example.bot_binnance.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.bot_binnance.common.CommonUtils;
import com.example.bot_binnance.dto.ResultDto;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý lỗi validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultDto<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        });

    	ResultDto<String> result = new ResultDto<String>(500, "validation faid", errorMessage.toString());
    	return CommonUtils.RESULT_OK(result);
    }

    // Xử lý các lỗi chung chung khác nếu cần
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultDto<String>> handleGlobalException(Exception ex) {
        ResultDto<String> result = new ResultDto<>(500, "Internal Server Error: " + ex.getMessage(), null);
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}	
