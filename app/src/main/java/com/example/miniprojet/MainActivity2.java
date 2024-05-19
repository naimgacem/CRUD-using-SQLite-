package com.example.miniprojet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private DatabaseHelper DBHelper;
    private ArrayList<Post> posts;
    private CustomAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        posts = new ArrayList<>();
        adapter = new CustomAdapter(this,posts);

        // creating a new dbhandler class
        // and passing our context to it.
        DBHelper = new DatabaseHelper(MainActivity2.this);
        DBHelper.fillListview( posts, adapter);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        Button addPostButton = findViewById(R.id.add_post_button);
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });





    }
}