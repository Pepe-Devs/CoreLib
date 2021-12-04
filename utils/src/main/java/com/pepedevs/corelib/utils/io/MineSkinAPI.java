package com.pepedevs.corelib.utils.io;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MineSkinAPI {

    private static final String URL_FORMAT = "https://api.mineskin.org/get/uuid/%s";

    public static String[] getTextureProperties(String id) {

        try {
            //Open http connection
            HttpURLConnection textureConnection = (HttpURLConnection) new URL(String.format(URL_FORMAT, id)).openConnection();
            textureConnection.setRequestProperty("User-Agent", "JustixDevelopment/APIClient");
            textureConnection.setRequestMethod("GET");
            textureConnection.setReadTimeout(5000);

            //Parse response
            BufferedReader reader = new BufferedReader(new InputStreamReader(textureConnection.getInputStream()));
            JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
            JsonObject texture = jsonObject.get("data").getAsJsonObject().get("texture").getAsJsonObject();

            String[] property = new String[]{texture.get("value").getAsString(), texture.get("signature").getAsString()};
            reader.close();
            textureConnection.disconnect();

            return property;
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return new String[]{null, null};
    }

}
