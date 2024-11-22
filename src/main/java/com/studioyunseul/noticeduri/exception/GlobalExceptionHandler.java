package com.studioyunseul.noticeduri.exception;

import com.studioyunseul.noticeduri.web.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final SessionManager sessionManager;

    public GlobalExceptionHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @ExceptionHandler(MemberNotFound.class)
    public String memberNotFound(HttpServletRequest request) {
        sessionManager.expireSession(request);
//        expireCookie(response, "memberId");
        return "redirect:/members/login";
    }
}
