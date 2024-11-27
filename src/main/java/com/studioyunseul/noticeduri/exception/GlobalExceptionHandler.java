package com.studioyunseul.noticeduri.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler(MemberNotFound.class)
    public String memberNotFound() {
        return "redirect:/members/login";
    }
}
