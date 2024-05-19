package com.example.miniprojet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Update extends AppCompatActivity {

    private EditText oldPasswordEditText, newPasswordEditText, usernameedit;
    private String username, password;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        username = getIntent().getStringExtra("USERNAME");
        password = getIntent().getStringExtra("PASSWORD");

        dbHelper = new DatabaseHelper(this);
        usernameedit = findViewById(R.id.username);
        oldPasswordEditText = findViewById(R.id.editText_old_password);
        newPasswordEditText = findViewById(R.id.editText_new_password);
        oldPasswordEditText.setText(password);
        usernameedit.setText(username);
    }

    public void updateinfo(View c) {
        String oldPassword = oldPasswordEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();

        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(Update.this, "Please enter old and new password", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dbHelper.isValidPassword(username, oldPassword)) {
            dbHelper.updatePassword(username, newPassword);
            Toast.makeText(Update.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Update.this, "Incorrect old password or user does not exist", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View v){
        Intent ab = new Intent(this, MainActivity.class);
        startActivity(ab);
    }
}
