package com.example.customerrenting.Services.PushNotifications;

import android.app.DownloadManager;
import android.content.Context;
import android.os.StrictMode;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FCMSend {
    private static String BASA_URL = "https://fcm.googleapis.com/fcm/send";
    private static String SERVER_KEY;

    static {
        try {
            // Load the server key from the config.properties file
            InputStream inputStream = FCMSend.class.getClassLoader().getResourceAsStream("config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            SERVER_KEY = properties.getProperty("server_key");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pushNotification(Context context, String token, String title, String message) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(context);

        try{
            JSONObject json = new JSONObject();
            json.put("to", token);
            JSONObject notification = new JSONObject();
            notification.put("title", title);
            notification.put("body", message);
            json.put("notification", notification);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASA_URL, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("FCM" + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Context-Type", "application/json");
                    params.put("Authorization", "key=" + SERVER_KEY);
                    return params;
                }

            };
            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
