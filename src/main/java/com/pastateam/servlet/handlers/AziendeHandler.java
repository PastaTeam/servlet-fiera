package com.pastateam.servlet.handlers;

import com.pastateam.dbinterface.IAziende;
import com.pastateam.model.Azienda;
import com.pastateam.servlet.PathHandler;
import java.util.List;
import dbclasses.AziendaRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AziendeHandler extends PathHandler {
    IAziende aziendeRepo = new AziendaRepository();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONArray list = new JSONArray();

        List<Azienda> listaAziende = aziendeRepo.getListaAziende();
        for (Azienda azienda: listaAziende) {
            JSONObject object = new JSONObject();
            object.put("nome", azienda.getNome());
            object.put("ID", azienda.getID());

            list.add(object);
        }

        resp.getWriter().println(list.toJSONString());
    }
}
