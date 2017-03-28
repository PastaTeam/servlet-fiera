package com.pastateam.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class PathHandler {
    public static void replyWithError (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().println("{\"error\": \"Unknown path\"}");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        replyWithError(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        replyWithError(req, resp);
    }

    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        replyWithError(req, resp);
    }

    public void doDelete (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        replyWithError(req, resp);
    }

    public final void doOptions (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
