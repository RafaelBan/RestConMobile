package com.example.restconmobile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_logout);
        actionBar.setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { showCustomDialog(); }
        });
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_cod_masa);

        final EditText codView = dialog.findViewById(R.id.codMasa);
        Button submitButton = dialog.findViewById(R.id.submit);
        Button cancelButton = dialog.findViewById(R.id.cancel);

        submitButton.setOnClickListener((View v) -> {
            String cod = String.valueOf(codView.getText());
            if (!cod.equals("")) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[1];
                        field[0] = "cod";
                        String[] data = new String[1];
                        data[0] = cod;
                        PutData putData = new PutData("https://hosting2062588.online.pro/mobile/codMasa.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String savedPut = putData.getResult();
                                if(savedPut.charAt(0) == '0') {
                                    Intent intent = new Intent(getApplicationContext(), ComandaMenu.class);
                                    intent.putExtra("cod", cod);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), savedPut, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
            }
            dialog.dismiss();
        });

        cancelButton.setOnClickListener((View v) -> {dialog.dismiss();});
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), com.example.restconmobile.Login.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}