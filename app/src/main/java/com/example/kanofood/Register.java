package com.example.kanofood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Register extends AppCompatActivity {

    TextInputEditText teName,  teEmail, teNumber, tePassword;
    Button buttonRegister;
    TextView tvLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        teName = findViewById(R.id.nama);
        teEmail = findViewById(R.id.email);
        teNumber = findViewById(R.id.nomor);
        tePassword = findViewById(R.id.password);
        buttonRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama_user, email, nomor , password;
                nama_user = String.valueOf(teName.getText());
                email = String.valueOf(teEmail.getText());
                nomor = String.valueOf(teNumber.getText());
                password = String.valueOf(tePassword.getText());

                if (!nama_user.equals("") && !email.equals("") && !nomor.equals("") && !password.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "nama_user";
                            field[1] = "email";
                            field[2] = "nomor";
                            field[3] = "password";
                            //Creating array for data
                            String[] data = new String[4];
                            data[0] = nama_user;
                            data[1] = email;
                            data[2] = nomor;
                            data[3] = password;
                            PutData putData = new PutData("http://192.168.1.5/Kanofood/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")){
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
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}