package com.studioyunseul.noticeduri.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.studioyunseul.noticeduri.utils.CookieUtil.expireCookie;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemberNotFound.class)
    public String memberNotFound(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/members/login";
    }
}
