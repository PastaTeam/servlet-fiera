package com.pastateam.servlet.handlers;

import com.pastateam.servlet.PathHandler;
import com.pastateam.servlet.utils.AuthUtils;
import com.pastateam.servlet.utils.LoggedUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutHandler extends PathHandler {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LoggedUser loggedUser = AuthUtils.checkLogged(req);
        if (loggedUser == null) {
            resp.getWriter().println("{}");
            return;
        }

        AuthUtils.logout(AuthUtils.getAuthToken(req));
        resp.getWriter().println("{}");
    }
}
