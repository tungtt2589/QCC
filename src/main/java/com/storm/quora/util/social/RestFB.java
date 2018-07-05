package com.storm.quora.util.social;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;

/*import org.apache.http.client.fluent.Request;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;*/

public class RestFB {
    public static String FACEBOOK_APP_ID = "278025672770919";
    public static String FACEBOOK_APP_SECRET = "572220ee339b0ce81df9100a4ebee0e9";
    public static String FACEBOOK_REDIRECT_URL = "https://localhost:8888/";
    public static String FACEBOOK_LINK_GET_TOKEN = "https://graph.facebook.com/oauth/access_token?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s";

    public static String getToken (final String code) throws Exception{
        String link = String.format(FACEBOOK_LINK_GET_TOKEN,FACEBOOK_APP_ID,FACEBOOK_APP_SECRET,FACEBOOK_REDIRECT_URL, code);

        HttpResponse<JsonNode> response = Unirest.post(link)
                .header("content-type", "application/json")
                .body("{}").asJson();

        JSONObject myObj = response.getBody().getObject();

        /*String response = Request.Get(link).execute().returnContent().asString();
        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");*/
        String accessToken = "";
        return accessToken;
    }

    /*public static User getUserInfo(String accessToken){
        FacebookClient facebookClient = new DefaultFacebookClient(accessToken, FACEBOOK_APP_SECRET, Version.LATEST);
        return facebookClient.fetchObject("me", User.class);
    }*/
}
