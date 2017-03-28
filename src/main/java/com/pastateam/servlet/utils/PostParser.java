package com.pastateam.servlet.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class PostParser {
    public static JSONObject parseJson (HttpServletRequest req) {
        StringBuilder jb = new StringBuilder();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);

            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(jb.toString());
        } catch (Exception e) {
            return new JSONObject();
        }
    }
}
