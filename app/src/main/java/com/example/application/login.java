package com.example.application;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class login extends AppCompatActivity {
    private TextView tvRegister;
    private EditText etLoginGmail,etLoginPassword;
    Button btnLogin;

    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        tvRegister = findViewById(R.id.tvRegister);
        etLoginGmail = findViewById(R.id.etLogGmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginGmail.getText().toString().trim();
                String password = etLoginPassword.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty() ) {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_container));
                    TextView text = (TextView) layout.findViewById(R.id.textCustom);
                    text.setText("Please Fill email and password correctly");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                } else {
                    cursor = db.rawQuery("SELECT *FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_5 + "=?", new String[]{email, password});
                    if (cursor != null) {
                        if (cursor.getCount() > 0) {
                            Toast.makeText(getApplicationContext(), "Login sucess", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);

                        } else {
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_container));
                            TextView text = (TextView) layout.findViewById(R.id.textCustom);
                            text.setText("Wrong email and password");
                            Toast toast = new Toast(getApplicationContext());
                            toast.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK, 0, 0);
                            toast.setDuration(Toast.LENGTH_LONG);
                            toast.setView(layout);
                            toast.show();
                        }
                    }
                }



            }
        });



        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}