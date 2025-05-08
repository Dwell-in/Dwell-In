package com.ssafy.home.common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieHelper {
    public default Cookie setupCookie(String name, String value, int maxAge, String path, HttpServletResponse resp) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        if (path != null) {
            cookie.setPath(path);
        }
        resp.addCookie(cookie);
        return cookie;
    }
}
