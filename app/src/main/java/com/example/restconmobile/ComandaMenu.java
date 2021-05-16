package com.example.restconmobile;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ComandaMenu extends AppCompatActivity {

    public static String[] prod;
    public static String[] prod2;
    public static String numeMasa;
    public static int nrProd;
    public static int valoare;
    public static ArrayList<String> produseFinal = new ArrayList<>();
    public static String prodType;
    public static String[] produseDataBase;
    public static String cod;
    public static  BottomNavigationView navView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_menu);
        navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_mancare, R.id.navigation_bautura)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        FloatingActionButton fab = findViewById(R.id.fabComanda);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomDialog();
            }
        });
        Intent intent = getIntent();
        getProduse();
        cod = intent.getStringExtra("cod");
    }

    public static void getProduse() {
        String[] field = new String[0];
        String[] data = new String[0];
        PutData putData = new PutData("https://hosting2062588.online.pro/mobile/produse.php", "POST", field, data);
        String savedPut;
        if (putData.startPut()) {
            if (putData.onComplete()) {
                savedPut = putData.getResult();
                if (savedPut.charAt(0) == '1') {
                    ComandaMenu.produseDataBase = savedPut.split("~");
                }
            }
        }
    }

    void showCustomDialog(){
        final Dialog dialog = new Dialog(ComandaMenu.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_finalizare);
        Button plataCardButton = dialog.findViewById(R.id.cardBancar);
        Button plataCash = dialog.findViewById(R.id.plataCash);
        Button cancelButton = dialog.findViewById(R.id.cancel);
        plataCardButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(getApplicationContext(), PlataCard.class);
            startActivity(intent);
            finish();
        });
        plataCash.setOnClickListener((View v) -> {
            Intent intent = new Intent(getApplicationContext(), PlataCash.class);
            startActivity(intent);
            finish();
        });
        cancelButton.setOnClickListener((View v) -> {dialog.dismiss();});
        dialog.show();
    }
}