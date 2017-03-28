package com.pastateam.servlet.utils;

import java.util.UUID;

import com.pastateam.model.Azienda;
import com.pastateam.model.Utente;

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

    public static String logInUser (Utente utente) {
        String token = UUID.randomUUID().toString();
        authMap.put(token, new LoggedUser(utente.getID(), LoggedUser.UserType.PERSONA));

        return token;
    }

    public static String logInUser (Azienda azienda) {
        String token = UUID.randomUUID().toString();
        authMap.put(token, new LoggedUser(azienda.getID(), LoggedUser.UserType.AZIENDA));

        return token;
    }

    public static void logout (String token) {
        authMap.remove(token);
    }
}
