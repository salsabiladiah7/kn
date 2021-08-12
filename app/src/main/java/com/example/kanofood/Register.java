package com.example.kanofood;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    TextInputEditText teName, teNumber, teEmail, tePassword;
    RadioGroup rgGender;
    RadioButton rbLaki, rbPerempuan, rbGender;
    TextView tvLogin, tvTgl_Lahir;
    Button btnRegister;
    ProgressBar progressBar;
    String date;

    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        teName = findViewById(R.id.nama);
        teNumber = findViewById(R.id.nomor);
        teEmail = findViewById(R.id.email);
        tePassword = findViewById(R.id.password);
        rgGender = findViewById(R.id.gender);
        rbLaki = findViewById(R.id.btn_laki);
        rbPerempuan = findViewById(R.id.btn_perempuan);
        btnRegister = findViewById(R.id.btn_signup);
        progressBar = findViewById(R.id.progress);
        tvTgl_Lahir = findViewById(R.id.tgl_lahir);
        tvLogin = findViewById(R.id.tv_login);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvTgl_Lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Register.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                date = day + "/" + month + "/" + year;
                tvTgl_Lahir.setText(date);
            }
        };

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int idradio = rgGender.getCheckedRadioButtonId();
                rbGender = findViewById(idradio);

                String nama, nomor, gender, tgl, email, password;
                nama = String.valueOf(teName.getText());
                nomor = String.valueOf(teNumber.getText());
                gender = String.valueOf(rbGender.getText());
                tgl = String.valueOf(tvTgl_Lahir.getText());
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
                            field[4] = "email";
                            field[5] = "password";
                            //Creating array for data
                            String[] data = new String[6];
                            data[0] = nama;
                            data[1] = nomor;
                            data[2] = gender;
                            data[3] = tgl;
                            data[4] = email;
                            data[5] = password;
                            PutData putData = new PutData("http://192.168.1.12/Kanofood2/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}