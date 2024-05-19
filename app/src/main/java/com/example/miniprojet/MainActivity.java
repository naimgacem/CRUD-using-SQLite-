package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText username;
    Button login, register;
    EditText password;
    Button list ;
    public DatabaseHelper dbHelper;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        login = findViewById(R.id.read);
        register = findViewById(R.id.create);
        password = findViewById(R.id.Password);
        list = findViewById(R.id.list);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create(v);
            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);


            }
        });
    }

    public void create(View v) {
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (dbHelper.isUserExists(user)) {
            Toast.makeText(MainActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.addNewUser(user, pass);
            Toast.makeText(MainActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View a) {
        String useer = username.getText().toString().trim();
        String paass = password.getText().toString().trim();

        if (dbHelper.isValidUser(useer, paass)) {
            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View v) {
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.isValidUser(user, pass)) {
            dbHelper.deleteUser(user);
            Toast.makeText(MainActivity.this, "User deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
    public void update(View v){
        Intent ab = new Intent(this, Update.class);
        String userame = username.getText().toString().trim();
        String passord = password.getText().toString().trim();

        ab.putExtra("USERNAME", userame);
        ab.putExtra("PASSWORD", passord);

        startActivity(ab);
    }
    public void gobraodcast(View zz){
        Intent broad = new Intent(this, Broadcast.class);
        startActivity(broad);
    }





}
