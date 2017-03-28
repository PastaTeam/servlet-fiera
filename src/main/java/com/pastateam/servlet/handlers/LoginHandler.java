package com.pastateam.servlet.handlers;

import com.pastateam.dbinterface.IAziende;
import com.pastateam.dbinterface.IUtenti;
import com.pastateam.model.Azienda;
import com.pastateam.model.Utente;
import com.pastateam.servlet.PathHandler;
import com.pastateam.servlet.utils.AuthUtils;
import com.pastateam.servlet.utils.PostParser;
import dbclasses.AziendaRepository;
import dbclasses.UtentiRepository;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginHandler extends PathHandler {
    private IAziende aziendeRepository = new AziendaRepository();
    private IUtenti utentiRepository = new UtentiRepository();

    private final String WRONG_PASSWORD = "{\"error\": \"wrong password\"}";
    private final String WRONG_EMAIL = "{\"error\": \"wrong email\"}";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject postParams = PostParser.parseJson(req);

        String type = (String) postParams.get("type");
        if (type.equals("persona")) {
            Utente utente = utentiRepository.getUtenteFromEmail((String) postParams.get("email"));
            if (utente == null) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().println(WRONG_EMAIL);
                return;
            }

            if (utente.checkPassword((String) postParams.get("password"))) {
                JSONObject response = new JSONObject();

                String token = AuthUtils.logInUser(utente);

                response.put("error", null);
                response.put("id", utente.getID());
                response.put("token", token);

                JSONObject account = new JSONObject();
                account.put("email", utente.getEmail());
                account.put("first_name", utente.getNome());
                account.put("last_name", utente.getCognome());
                account.put("role", "persona");

                response.put("account", account);

                resp.getWriter().println(response.toJSONString());

            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().println(WRONG_PASSWORD);
                return;
            }
        } else { // azienda
            Azienda azienda = aziendeRepository.getAziendaFromEmail((String) postParams.get("email"));
            if (azienda == null) {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().println(WRONG_EMAIL);
                return;
            }

            if (azienda.checkPassword((String) postParams.get("password"))) {
                JSONObject response = new JSONObject();

                String token = AuthUtils.logInUser(azienda);

                response.put("error", null);
                response.put("id", azienda.getID());
                response.put("token", token);

                JSONObject account = new JSONObject();
                account.put("email", azienda.getEmail());
                account.put("nome", azienda.getNome());
                account.put("role", "azienda");

                response.put("account", account);

                resp.getWriter().println(response.toJSONString());
            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                resp.getWriter().println(WRONG_PASSWORD);
                return;
            }
        }
    }
}
