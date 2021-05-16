package com.example.restconmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class PlataCash extends AppCompatActivity {

    public String[] prod2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plata_cash);
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String savedPut;
                String[] field = new String[1];
                field[0] = "cod";
                String[] data = new String[1];
                data[0] = ComandaMenu.cod;
                PutData putData = new PutData("https://hosting2062588.online.pro/mobile/codMasa.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        savedPut = putData.getResult();
                        if (savedPut.charAt(0) == '0') {
                            String[] masa = savedPut.split("\\s+");
                            prod2 = masa[2].split(",");
                        }
                    }
                }
                if (prod2[0].equals("gol")) {
                    handler.removeCallbacks(this);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                handler.postDelayed(this, 3000);
            }
        };
        handler.post(runnable);
    }
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}