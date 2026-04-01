package kr.or.ddit.mvc.arguments;

import java.io.IOException;
import java.net.URLDecoder;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookieValueResolver {
    public String resolveCookieValue(String cookieName, HttpServletRequest req) throws IOException {
        Cookie[] cookies = req.getCookies();
        String cookieValue = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    break;
                }
            }
        }
        return cookieValue;
    }
}
