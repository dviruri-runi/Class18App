package com.example.api_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText locationET = findViewById(R.id.locationET);
        Button btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = locationET.getText().toString();
                locationET.setText("");
                performSearch(query);
            }
        });

    }

    private void performSearch(String query) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                String result = "";
                try {
                    URL api = new URL("https://maps.google.com/maps/api/geocode/json?address=" + query + "&key=AIzaSyC3CuDYBjtwGhAvOYDG4kKRYrSybuoIH24");
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    api.openStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        result += inputLine;
                    }
                    in.close();

                    Gson gson = new Gson();
                    JsonObject resultobj = gson.fromJson(result,JsonObject.class);
                    JsonObject location = resultobj.getAsJsonArray("results").get(0).getAsJsonObject()
                            .getAsJsonObject("geometry").getAsJsonObject("location");
                    String lat = location.get("lat").getAsString();
                    String lng = location.get("lng").getAsString();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            TextView resultTv = findViewById(R.id.resultTV);
                            resultTv.setText(lat+","+lng);
                            Intent i = new Intent(MainActivity.this,MapActivity.class);
                            i.putExtra("lat",lat);
                            i.putExtra("lng",lng);
                            i.putExtra("query",query);
                            startActivity(i);
                        }
                    });

                } catch (Exception e) {

                }
            }
        });
    }
}