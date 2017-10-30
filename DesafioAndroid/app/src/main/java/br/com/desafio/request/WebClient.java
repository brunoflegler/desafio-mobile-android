package br.com.desafio.request;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WebClient {

    private final String link;


    public WebClient(String  link) {
        this.link = link;
    }


    public String get() {
        try {
            URL _url = new URL(link);
            HttpURLConnection urlConnection = (HttpURLConnection) _url.openConnection();

            try{
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            }finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            return null;
        }
    }

}
