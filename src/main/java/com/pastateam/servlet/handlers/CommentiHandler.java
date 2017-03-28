package com.pastateam.servlet.handlers;

import com.pastateam.dbinterface.IAziende;
import com.pastateam.dbinterface.ICommenti;
import com.pastateam.dbinterface.IProdotti;
import com.pastateam.dbinterface.IUtenti;
import com.pastateam.model.Azienda;
import com.pastateam.model.Commento;
import com.pastateam.model.Prodotto;
import com.pastateam.model.Utente;
import com.pastateam.servlet.PathHandler;
import com.pastateam.servlet.utils.AuthUtils;
import com.pastateam.servlet.utils.LoggedUser;
import com.pastateam.servlet.utils.PostParser;
import dbclasses.AziendaRepository;
import dbclasses.CommentiRepository;
import dbclasses.ProdottiRepository;
import dbclasses.UtentiRepository;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommentiHandler extends PathHandler {
    private final Pattern regExIdPattern = Pattern.compile("/commenti/([0-9]*)");

    private ICommenti commentiRepo = new CommentiRepository();
    private IUtenti utentiRepo = new UtentiRepository();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Matcher matcher;

        matcher = regExIdPattern.matcher(uri);
        if (matcher.find()) {
            Integer idProdotto = Integer.parseInt(matcher.group(1));
            sendCommenti(req, resp, idProdotto);

            return;
        }

        resp.getWriter().println("[]");
    }

    @Override
    public void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject postParams = PostParser.parseJson(req);

        LoggedUser loggedUser = AuthUtils.checkLogged(req);
        if (loggedUser == null || loggedUser.getType() != LoggedUser.UserType.PERSONA) {
            // TODO: errore
            return;
        }

        Integer ID = commentiRepo.addCommento((String) postParams.get("commento"), ((Long) postParams.get("prodotto")).intValue(), loggedUser.getID());
        resp.getWriter().println("{}");
    }

    private void sendCommenti (HttpServletRequest req, HttpServletResponse resp, Integer idProdotto) throws IOException {
        JSONArray list = new JSONArray();

        List<Commento> commenti = commentiRepo.getCommentiForProdotto(idProdotto);
        for (Commento commento: commenti) {
            JSONObject object = new JSONObject();

            object.put("commento", commento.getCommento());

            JSONObject utenteObj = new JSONObject();
            Utente utente = utentiRepo.getUtenteFromID(commento.getID_Utente());
            if (utente != null) {
                utenteObj.put("nome", utente.getNome());
                utenteObj.put("cognome", utente.getCognome());
            }

            object.put("persona", utenteObj);

            list.add(object);
        }

        resp.getWriter().println(list.toJSONString());
    }
}
