package com.storm.quora.controller;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.storm.quora.common.GooglePojo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class GoogleUtils {
    public static String GOOGLE_APP_ID = "930932659996-9jvtvg7igq7fm5574b2f5qlbdbm3vh9d.apps.googleusercontent.com";
    public static String GOOGLE_APP_SECRET = "K5k99fWhGPulPTl0Zi_heN8l";
    public static String GOOGLE_REDIRECT_URL = "http://localhost:8888/";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

    public static String getToken(final String code) throws Exception {
        HttpResponse<JsonNode> response = Unirest.post(GOOGLE_LINK_GET_TOKEN)
                .header("content-type", "application/x-www-form-urlencoded")
                .body("{\n\"grant_type\":\"authorization_code\"," +
                        "\n\"code\": \"" + code + "\"," +
                        "\n\"client_id\": \"" + GOOGLE_APP_ID + "\"," +
                        "\n\"client_secret\": \"" + GOOGLE_APP_SECRET + "\"," +
                        "\n\"redirect_uri\": \"" + GOOGLE_REDIRECT_URL + "\"\n}").asJson();

        JSONObject myObj = response.getBody().getObject();

        String accessToken = "";
        return accessToken;
    }

    public static GooglePojo getUserInfo(final String accessToken) throws Exception{
        String link = GOOGLE_LINK_GET_USER_INFO + accessToken;

        HttpResponse<JsonNode> response = Unirest.get(link)
                .header("content-type", "application/x-www-form-urlencoded")
                .asJson();

        JSONObject myObj = response.getBody().getObject();

        GooglePojo googlePojo = new GooglePojo();

        return googlePojo;
    }
}
