package com.pastateam.servlet.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class AuthUtils {
    public static Map<String, LoggedUser> authMap = new HashMap<>();

    public static String getAuthToken(HttpServletRequest req) {
        return req.getHeader("x-authorization-token");
    }

    public static LoggedUser checkLogged (HttpServletRequest req) {
        return authMap.get(getAuthToken(req));
    }
}
