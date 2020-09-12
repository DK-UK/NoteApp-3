package com.example.noteapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.noteapp.Database.AdminDatabaseHelper;
import com.example.noteapp.R;

public class MainActivity extends AppCompatActivity {

    //EditText Username, Password;
    //Button Button_Login, Button_Register;
    //public static AdminDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        Username = findViewById(R.id.et_name);
        Password = findViewById(R.id.et_pass);
        Button_Login = findViewById(R.id.btn_login);
        Button_Register = findViewById(R.id.btn_register);
        databaseHelper = new AdminDatabaseHelper(this);
        databaseHelper.createDataBase();
        databaseHelper.openDatabase();

        Button_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserNameValue = Username.getText().toString();
                String PasswordValue = Password.getText().toString();

                if (databaseHelper.ReadFromDb(UserNameValue, PasswordValue)){
                    Intent intent = new Intent(MainActivity.this, BaseActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

 */

    }


}