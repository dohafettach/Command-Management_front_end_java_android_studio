package com.example.gestion_de_stock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ArticleActivity extends AppCompatActivity {
    TextView lib , prix , cat_des;
    Integer myid ;
    String dnsUrl = "http://192.168.1.58:8080";
    String x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        lib = findViewById(R.id.pro_lib);
        prix = findViewById(R.id.pro_prix);
        cat_des = findViewById(R.id.cat_designation);
        Intent Intent = getIntent();
        cat_des.setText(
            Intent.getStringExtra("chosenCategory")
        );
        x = Intent.getStringExtra("chosenId");
        myid = Integer.valueOf(x);
    }

    public void articlePostRequest( String l , String p , String idd) throws Exception {
        String tmp = dnsUrl + "/api/article";
        URL url = new URL(tmp);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");

        String jsonInputString = "{\"libelle\": \""+ l + "\", \"prix_unitaire\": "+ p +" ,\"category\" :  {\"id\" :  "+idd+"}}";
        try(OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }

    public void getIputValues(View v){
        String article_libile = lib.getText().toString();
        String article_prix = prix.getText().toString();
        Double art_prix = Double.parseDouble(article_prix);
        try{
            articlePostRequest(article_libile , article_prix , x);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void retunBack(View v) { finish(); }
}