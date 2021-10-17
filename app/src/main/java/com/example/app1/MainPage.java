package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.app1.journal.Journal3;
import com.example.app1.news.news1;
import com.example.app1.todoList.todo1;
import com.example.app1.weather.weather1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import util.JournalApi;

public class MainPage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    private FirebaseUser user;
    Button homeButton,signOutButton;
    TextView nameUp;

    Button weatherButton,journalButton,todoListButton,newsButton,gameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        weatherButton = findViewById(R.id.weatherButton);
        journalButton = findViewById(R.id.journalButton);
        todoListButton = findViewById(R.id.todoListButton);
        newsButton = findViewById(R.id.newsButton);
        gameButton = findViewById(R.id.gameButton);
        nameUp =findViewById(R.id.nameUp);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        nameUp.setText("Hi "+JournalApi.getInstance().getUsername());

        signOutButton = findViewById(R.id.signOutButton);


        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null && firebaseAuth != null) {
                    firebaseAuth.signOut();

                    startActivity(new Intent(MainPage.this,
                            login.class));
                    //finish();
                }

            }
        });

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, weather1.class));
            }
        });
        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, Journal3.class));
            }
        });
        todoListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, todo1.class));
            }
        });
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, news1.class));
            }
        });
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(login.this, signUp.class));
            }
        });
    }
}