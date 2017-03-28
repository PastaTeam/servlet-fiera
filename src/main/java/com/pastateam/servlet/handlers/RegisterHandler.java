package com.pastateam.servlet.handlers;

import com.pastateam.dbinterface.IAziende;
import com.pastateam.dbinterface.IUtenti;
import com.pastateam.servlet.PathHandler;
import com.pastateam.servlet.utils.PostParser;
import dbclasses.AziendaRepository;
import dbclasses.UtentiRepository;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterHandler extends PathHandler {
    private IAziende aziendeRepository = new AziendaRepository();
    private IUtenti utentiRepository = new UtentiRepository();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject postParams = PostParser.parseJson(req);

        if (req.getRequestURI().contains("persona"))
            registerPersona(postParams);
        else
            registerAzienda(postParams);

        resp.getWriter().println("{}");
    }

    private void registerPersona(JSONObject postData) {
        utentiRepository.addUtente(
                (String) postData.get("nome"),
                (String) postData.get("cognome"),
                (String) postData.get("email"),
                (String) postData.get("password")
        );
    }

    private void registerAzienda(JSONObject postData) {
        aziendeRepository.addAzienda(
                (String) postData.get("nome"),
                (String) postData.get("email"),
                (String) postData.get("password")
        );
    }
}
