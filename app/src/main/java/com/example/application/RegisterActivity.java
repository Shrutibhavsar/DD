package com.example.application;

import android.content.ContentValues;
import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {
    private Button registerBtn,gotoLoginBtn;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private EditText regName,regPhone,regGmail,regPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        openHelper = new DatabaseHelper(this);

        registerBtn = findViewById(R.id.btnRegLogin);
        gotoLoginBtn = findViewById(R.id.btnGotoLogin);
        regName = findViewById(R.id.etRegName);
        regPhone = findViewById(R.id.etRegPhone);
        regGmail = findViewById(R.id.etRegGmail);
        regPassword = findViewById(R.id.etRegPassword);
        db = openHelper.getWritableDatabase();
        String fname = regName.getText().toString().trim();
        String fPhone = regPhone.getText().toString().trim();
        String fGmail = regGmail.getText().toString().trim();
        String fPassword = regPassword.getText().toString().trim();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fname.isEmpty() || fPassword.isEmpty() || fGmail.isEmpty() || fPhone.isEmpty()) {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.customtoastlayout, (ViewGroup) findViewById(R.id.custom_toast_container));
                    TextView text = (TextView) layout.findViewById(R.id.textCustom);
                    text.setText("Please fill details ");
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                } else {
                    insertData(fname,fPhone,fGmail,fPassword);
                    Toast.makeText(getApplicationContext(),"Registration Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), login.class);
                    startActivity(intent);
                }
            }
        });


        
    }
    public void insertData(String fname,String fPhone,String fGmail,String fPassword){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2,fname);
        contentValues.put(DatabaseHelper.COL_3,fPhone);
        contentValues.put(DatabaseHelper.COL_4,fGmail);
        contentValues.put(DatabaseHelper.COL_5,fPassword);

        long id = db.insert(DatabaseHelper.TABLE_NAME,null,contentValues);
    }
}