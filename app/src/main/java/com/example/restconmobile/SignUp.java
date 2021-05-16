package com.example.restconmobile;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import static com.example.restconmobile.R.layout.activity_sign_up;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextUsername, textInputEditTextPrenume, textInputEditTextNume, textInputEditTextPassword, textInputEditTextMail;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPrenume = findViewById(R.id.prenume);
        textInputEditTextNume = findViewById(R.id.nume);
        textInputEditTextMail = findViewById(R.id.mail);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username, prenume, nume, mail, password;
                    username = String.valueOf(textInputEditTextUsername.getText());
                    prenume = String.valueOf(textInputEditTextPrenume.getText());
                    nume = String.valueOf(textInputEditTextNume.getText());
                    mail = String.valueOf(textInputEditTextMail.getText());
                    password = String.valueOf(textInputEditTextPassword.getText());

                    if(!username.equals("") && !prenume.equals("") && !nume.equals("") && !mail.equals("") && !password.equals("")) {

                       progressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String[] field = new String[5];
                                field[0] = "username";
                                field[1] = "prenume";
                                field[2] = "nume";
                                field[3] = "password";
                                field[4] = "mail";
                                String[] data = new String[5];
                                data[0] = username;
                                data[1] = prenume;
                                data[2] = nume;
                                data[3] = password;
                                data[4] = mail;
                                PutData putData = new PutData("https://hosting2062588.online.pro/mobile/register.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        if(result.equals("Inregistrarea s-a efectuat cu succes!")) {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), Login.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Toate campurile sunt necesare.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
