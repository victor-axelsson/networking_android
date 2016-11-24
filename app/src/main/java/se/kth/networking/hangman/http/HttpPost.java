package se.kth.networking.hangman.http;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by victoraxelsson on 2016-11-24.
 */

public class HttpPost extends Http {

    HashMap<String, String> postDataParams;
    String response;

    public HttpPost(String uri, OnResponse<String> onResponse, HashMap<String, String> postDataParams) {
        super(uri, onResponse);
        this.postDataParams = postDataParams;
    }



    @Override
    protected Void doInBackground(Void... params) {
        URL url;
        try {
            url = new URL(uri);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString());

            writer.flush();
            writer.close();
            os.close();

            int responseCode = urlConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                response = readStream(urlConnection.getInputStream());
            }else{
                Log.e("HTTP", responseCode + "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public String getPostDataString() {
        String postData = new JSONObject(postDataParams).toString();
        return postData;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        onResponse.onResponse(response);
    }
}
