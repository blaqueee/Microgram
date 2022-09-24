package com.example.microgram.Utility;

import com.example.microgram.DTO.UserDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Optional;

public class CookieUtil {
    public static void addCookie(UserDto userDto, HttpServletResponse response) {
        var cookie = makeCookie(userDto.getUsername());
        response.addCookie(cookie);
    }

    public static Optional<String> getUsernameFromCookie(HttpServletRequest request) {
        var optionalCookie = getAuthorizationCookie(request);
        if (optionalCookie.isEmpty())
            return Optional.empty();
        String decoded = optionalCookie.get().getValue();
        String username = Utils.decode(decoded, decoded.length());
        return Optional.of(username);
    }

    private static Optional<Cookie> getAuthorizationCookie(HttpServletRequest request) {
        if (Objects.nonNull(request.getCookies())) {
            var cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("authorized".equals(cookie.getName()))
                    return Optional.of(cookie);
            }
        }
        return Optional.empty();
    }

    private static Cookie makeCookie(String username) {
        Cookie cookie = new Cookie("authorized", Utils.encode(username, username.length()));
        cookie.setMaxAge(300);
        cookie.setHttpOnly(true);
        return cookie;
    }
}
