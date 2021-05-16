package com.example.restconmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.restconmobile.ui.comanda.ComandaFragment;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class PlataCard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plata_card);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Button finalizare = findViewById(R.id.buttonFinalizare);
        finalizare.setOnClickListener((View v) -> {
            String[] field = new String[4];
            field[0] = "numeM";
            field[1] = "comanda";
            field[2] = "nrProd";
            field[3] = "valoare";
            String[] data = new String[4];
            data[0] = ComandaMenu.numeMasa;
            data[1] = "gol";
            data[2] = "0";
            data[3] = "0";
            PutData putData = new PutData("https://hosting2062588.online.pro/mobile/updateMasa.php", "POST", field, data);
            if (putData.startPut()) {
                if (putData.onComplete()) {
                    String result = putData.getResult();
                    if (result.equals("Updatarea s-a efectuat cu succes!")) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), ComandaMenu.class);
                intent.putExtra("cod", ComandaMenu.cod);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}