package com.pastateam.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletEntryPoint extends HttpServlet {
    private String cleanPath(String path) {
        String tmp = path.replace("/api/", "");
        int index = tmp.indexOf('/');

        if (index > -1)
            tmp = tmp.substring(0, index);

        return tmp;
    }

    private void setHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");

        response.setContentType("application/json");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = cleanPath(request.getRequestURI());
        PathHandler handler = ServletRepository.pathMap.get(path);

        if (handler == null) {
            PathHandler.replyWithError(request, response);
            return;
        }

        setHeaders(response);
        handler.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = cleanPath(request.getRequestURI());
        PathHandler handler = ServletRepository.pathMap.get(path);

        if (handler == null) {
            PathHandler.replyWithError(request, response);
            return;
        }

        setHeaders(response);
        handler.doPost(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = cleanPath(request.getRequestURI());
        PathHandler handler = ServletRepository.pathMap.get(path);

        if (handler == null) {
            PathHandler.replyWithError(request, response);
            return;
        }

        setHeaders(response);
        handler.doPut(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = cleanPath(request.getRequestURI());
        PathHandler handler = ServletRepository.pathMap.get(path);

        if (handler == null) {
            PathHandler.replyWithError(request, response);
            return;
        }

        setHeaders(response);
        handler.doDelete(request, response);
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = cleanPath(request.getRequestURI());
        PathHandler handler = ServletRepository.pathMap.get(path);

        if (handler == null) {
            PathHandler.replyWithError(request, response);
            return;
        }

        setHeaders(response);
        handler.doOptions(request, response);
    }
}
