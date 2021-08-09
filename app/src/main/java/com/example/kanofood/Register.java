package com.example.kanofood;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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

public class Register extends AppCompatActivity {

    TextInputEditText teName, teNumber, teGender, teTgl, teEmail, tePassword;
    Button buttonRegister;
    TextView tvLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        teName = findViewById(R.id.nama);
        teNumber = findViewById(R.id.nomor);
        teGender = findViewById(R.id.gender);
        teTgl = findViewById(R.id.tgl);
        teEmail = findViewById(R.id.email);
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

                String nama, nomor, gender, tgl, email, password;
                nama = String.valueOf(teName.getText());
                nomor = String.valueOf(teNumber.getText());
                gender = String.valueOf(teGender.getText());
                tgl = String.valueOf(teTgl.getText());
                email = String.valueOf(teEmail.getText());
                password = String.valueOf(tePassword.getText());

                if (!nama.equals("") && !nomor.equals("") && !gender.equals("") && !tgl.equals("") && !email.equals("") && !password.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    Environment environment = new Environment();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[6];
                            field[0] = "name";
                            field[1] = "no_hp";
                            field[2] = "gender";
                            field[3] = "tanggal_lahir";
                            field[5] = "email";
                            field[6] = "password";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = nama;
                            data[1] = email;
                            data[2] = nomor;
                            data[3] = password;
                            PutData putData = new PutData(environment + "/Kanofood2/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
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