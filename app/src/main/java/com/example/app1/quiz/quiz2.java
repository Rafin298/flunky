package com.example.app1.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.app1.BuildConfig;
import com.example.app1.Dashboard;
import com.example.app1.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class quiz2 extends AppCompatActivity {

    CircularProgressBar circularProgressBar;
    TextView result;
    int correct,wrong;
    Button shareBtn,againBtn,exitBtn;
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        window=this.getWindow();
        window.setStatusBarColor(getColor(R.color.darkaction) );
        window.setNavigationBarColor(getColor(R.color.darknav) );
        circularProgressBar=findViewById(R.id.circularProgressBar);
        shareBtn=findViewById(R.id.shareBtn);
        againBtn=findViewById(R.id.againBtn);
        exitBtn=findViewById(R.id.exitBtn);
        result=findViewById(R.id.result);

        correct=getIntent().getIntExtra("correct",0);
        wrong=getIntent().getIntExtra("wrong",0);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nI got "+correct+" out of 10.You can also try\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
        circularProgressBar.setProgress(correct);
        result.setText(correct+"/10");

        againBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(quiz2.this,quiz1.class);
                startActivity(intent);
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(quiz2.this, Dashboard.class);
                startActivity(intent);
            }
        });
    }
}