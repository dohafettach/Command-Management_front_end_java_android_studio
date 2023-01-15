package com.example.gestion_de_stock;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.gestion_de_stock.models.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button button;
    List<Category> categoryList =null;
    List<Article> articlesList = null;
    String designationChosen = "";
    Integer categoryIdChosen;
    String dnsUrl = "http://192.168.1.58:8080";
    ArrayList<Article> resultatList = new ArrayList<>();
    ArticleAdapter adapter1;
    TextView totalArts ,somme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Article ListView
        ListView articleList = (ListView) findViewById(R.id.articleList);

        adapter1 = new ArticleAdapter(this, R.layout.list_layout, resultatList);
        articleList.setAdapter(adapter1);

        ArrayList<Integer> id = new ArrayList<Integer>();
        ArrayList<String> libelle = new ArrayList<String>();
        ArrayList<Double> bulletinNotes = new ArrayList<Double>();

        try{
            articlesList = articlesGetRequest(dnsUrl + "/api/article");
        } catch (Exception e) {
            e.printStackTrace();
        }


        totalArts = findViewById(R.id.total_arts);
        somme = findViewById(R.id.somme);

        //Go to Article page button
        button = (Button) findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openArticle();
            }
        });


        //Change spinner value
        try {
            categoryList = categoryGetRequest(dnsUrl + "/api/category");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Spinner Categories
//        List<Integer> categories = Arrays.asList(1,99,3,4);
        List<Integer> categories = new ArrayList<Integer>();
        for (Integer i = 0; i < categoryList.size(); i++) {
            categories.add(
                    categoryList.get(i).getId()
            );
        }

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public List<Category> categoryGetRequest(String urlToRead) throws Exception {
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String tmp = null;

        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                tmp = line;
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        List<Category> cc2 = Arrays.asList(mapper.readValue(tmp, Category[].class));
        return cc2;
    }

    public List<Article> articlesGetRequest(String urlToRead) throws Exception {
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String tmp = null;
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {tmp = line;}
        }
        ObjectMapper mapper = new ObjectMapper();
        List<Article> aa2 = Arrays.asList(mapper.readValue(tmp, Article[].class));
        for (Article a : aa2){ resultatList.add(a);}
        return aa2;
    }

    //Go to Article
    public void openArticle(){
        Intent intent = new Intent(this, ArticleActivity.class);
        intent.putExtra("chosenCategory",designationChosen);
        String tmp = String.valueOf(categoryIdChosen);
        intent.putExtra("chosenId",tmp);
        startActivity(intent);
    }

    //Changer les categories / les designations / et les tables
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = categoryList.get(i).getDesignation();
        ((TextView)findViewById(R.id.designation)).setText(text);
        designationChosen = text;
        categoryIdChosen = categoryList.get(i).getId();
        resultatList.clear();
        adapter1.notifyDataSetChanged();
        Integer total = 0;
        Double som = 0.0;
        for(Article a: articlesList){
            if(a.getCategory().getId() == categoryIdChosen){
                total +=1;
                som += a.getPrix_unitaire();
                resultatList.add(a);
                adapter1.notifyDataSetChanged();
            }
        }
        som = som * 28;
        totalArts.setText(total.toString());
        somme.setText(som.toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}