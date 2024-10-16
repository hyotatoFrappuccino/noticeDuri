package com.studioyunseul.noticeduri.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void addMemberCookie(HttpServletResponse response, Long memberId) {
        Cookie cookie = new Cookie("memberId", String.valueOf(memberId));
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
