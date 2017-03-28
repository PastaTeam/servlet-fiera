package com.pastateam.servlet.handlers;

import com.pastateam.dbinterface.IAziende;
import com.pastateam.dbinterface.IProdotti;
import com.pastateam.model.Azienda;
import com.pastateam.model.Prodotto;
import com.pastateam.servlet.PathHandler;
import com.pastateam.servlet.utils.AuthUtils;
import com.pastateam.servlet.utils.LoggedUser;
import com.pastateam.servlet.utils.PostParser;
import dbclasses.AziendaRepository;
import dbclasses.ProdottiRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProdottiHandler extends PathHandler {
    private final Pattern regExIdPattern = Pattern.compile("/prodotti/([0-9]*)");

    private IProdotti prodottiRepo = new ProdottiRepository();
    private IAziende aziendeRepo = new AziendaRepository();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Matcher matcher;

        matcher = regExIdPattern.matcher(uri);
        if (matcher.find()) {
            Integer idProdotto = Integer.parseInt(matcher.group(1));
            sendSingoloProdotto(req, resp, idProdotto);

            return;
        }

        sendListaProdotti(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject postParams = PostParser.parseJson(req);

        LoggedUser loggedUser = AuthUtils.checkLogged(req);
        if (loggedUser == null || loggedUser.getType() != LoggedUser.UserType.AZIENDA) {
            // TODO: errore
            return;
        }

        Integer ID = prodottiRepo.addProdotto(
                (String) postParams.get("nome"),
                (String) postParams.get("descrizione"),
                loggedUser.getID()
        );

        System.out.println(ID);

        resp.getWriter().println("{}");
    }

    private void sendSingoloProdotto(HttpServletRequest req, HttpServletResponse resp, Integer idProdotto) throws IOException {
        JSONObject object = new JSONObject();

        Prodotto prodotto = prodottiRepo.getProdottoFromID(idProdotto);
        if (prodotto != null) {
            Azienda azienda = aziendeRepo.getAziendaFromID(prodotto.getID_Azienda());

            JSONObject object_azienda = new JSONObject();
            if (azienda != null) {
                object_azienda.put("ID", azienda.getID());
                object_azienda.put("nome", azienda.getNome());
            }

            object.put("azienda", object_azienda);

            object.put("nome", prodotto.getNome());
            object.put("descrizione", prodotto.getDescrizione());
            object.put("ID", prodotto.getID());
        }

        resp.getWriter().println(object.toJSONString());
    }

    private void sendListaProdotti(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONArray list = new JSONArray();

        String azienda = req.getParameter("azienda");
        try {
            Integer aziendaId = Integer.parseInt(azienda);

            List<Prodotto> prodottoList = prodottiRepo.getListaProdottiAzienda(aziendaId);
            for (Prodotto prodotto : prodottoList) {
                JSONObject object = new JSONObject();

                object.put("nome", prodotto.getNome());
                object.put("descrizione", prodotto.getDescrizione());
                object.put("ID", prodotto.getID());

                list.add(object);
            }
        } catch (NumberFormatException e) {

        }

        resp.getWriter().println(list.toJSONString());
    }
}
