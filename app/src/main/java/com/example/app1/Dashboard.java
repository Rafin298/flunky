package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.app1.journal.Journal3;
import com.example.app1.journal.Journal4;
import com.example.app1.news.news1;
import com.example.app1.quiz.quiz1;
import com.example.app1.todoList.todo1;
import com.example.app1.weather.weather1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import util.JournalApi;

public class Dashboard extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    Window window;
    private FirebaseUser user;
    Button homeButton,signOutButton;
    TextView nameUp;

    Button weatherButton,journalButton,todoListButton,newsButton,gameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        window=this.getWindow();
        window.setStatusBarColor(getColor(R.color.darkaction) );
        window.setNavigationBarColor(getColor(R.color.darknav) );

        weatherButton = findViewById(R.id.btnweather);
        journalButton = findViewById(R.id.btnjournal);
        todoListButton = findViewById(R.id.btntodolist);
        newsButton = findViewById(R.id.btnnews);
        gameButton = findViewById(R.id.btnplayaquiz);

        nameUp =findViewById(R.id.nameUp);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        nameUp.setText("Hi "+ JournalApi.getInstance().getUsername());

        signOutButton = findViewById(R.id.signOutButton);


        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null && firebaseAuth != null) {
                    firebaseAuth.signOut();

                    startActivity(new Intent(Dashboard.this,
                            loginn.class));
                    //finish();
                }

            }
        });

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, weather1.class));
            }
        });
        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Journal4.class));
            }
        });
        todoListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, todo1.class));
            }
        });
        newsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, news1.class));
            }
        });
        gameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, quiz1.class));
            }
        });
    }
}