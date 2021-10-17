package com.example.app1.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.example.app1.Dashboard;
import com.example.app1.R;
import com.example.app1.login;
import com.example.app1.news.news1;
import com.example.app1.weather.weather1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import model.quiz.countryData;
import model.quiz.quizData;
import model.weather.ForecastData;
import model.weather.Weather;
import model.weather.WeatherList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.WeatherRecyclerAdapter;
import util.QuizApi;
import util.WeatherForecastApi;

public class quiz1 extends AppCompatActivity {
    CountDownTimer countDownTimer;

   RoundedHorizontalProgressBar progressBar;


    CardView cardA,cardB,cardC,cardD;
    ImageView flagImage,quesMark,backss;
    TextView optionA,optionB,optionC,optionD,timerText;
    Button nextButton,homeButton,signOutButton;
    List<countryData> countryDatas = new ArrayList<>();
    Window window;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    int timerValue=21;
   // int viewtimerValue=21;
    int correctCount=0;
    int wrongCount=0;
    int index=0;
    int quesCount=0;
    int nn=21000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);
        window=this.getWindow();
        window.setStatusBarColor(getColor(R.color.darkaction) );
        window.setNavigationBarColor(getColor(R.color.darknav) );
        progressBar=findViewById(R.id.quizTimer);
        timerText=findViewById(R.id.timerText);
        flagImage=findViewById(R.id.flagImage);
        quesMark=findViewById(R.id.quesMark);

        optionA=findViewById(R.id.optionA);
        optionB=findViewById(R.id.optionB);
        optionC=findViewById(R.id.optionC);
        optionD=findViewById(R.id.optionD);

        cardA=findViewById(R.id.cardA);
        cardB=findViewById(R.id.cardB);
        cardC =findViewById(R.id.cardC);
        cardD=findViewById(R.id.cardD);

        nextButton=findViewById(R.id.nextButton);

        backss = findViewById(R.id.backss);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        backss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(quiz1.this, Dashboard.class));
            }
        });


        countDownTimer =new CountDownTimer(nn,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerValue=timerValue-1;
               // String ss=timerValue.toString();

                timerText.setText(Integer.toString(timerValue));
                progressBar.setProgress(timerValue);

            }

            @Override
            public void onFinish() {
                Dialog dialog =new Dialog(quiz1.this);
                dialog.setContentView(R.layout.timeoutdialogue);

                dialog.findViewById(R.id.tryAgain).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(quiz1.this,quiz1.class);
                        startActivity(intent);
                    }
                });
                dialog.findViewById(R.id.exitbut).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(quiz1.this,Dashboard.class);
                        startActivity(intent);
                    }
                });
                dialog.show();

            }
        }.start();
        retrieveJsonForCurrent();
        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionA.getText()==countryDatas.get(index).getName()){
                    cardA.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.green));
                    correct();
                }else{
                    cardA.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.red));
                    wrong();
                }
                disableButoon();
            }
        });
        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionB.getText()==countryDatas.get(index).getName()){
                    cardB.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.green));
                    correct();
                }else{
                    cardB.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.red));
                    wrong();
                }
                disableButoon();
            }
        });
        optionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionC.getText()==countryDatas.get(index).getName()){
                    cardC.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.green));
                    correct();
                }else{
                    cardC.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.red));
                    wrong();
                }
                disableButoon();
            }
        });
        optionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(optionD.getText()==countryDatas.get(index).getName()){
                    cardD.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.green));
                    correct();
                }else{
                    cardD.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.red));
                    wrong();
                }
                disableButoon();
            }
        });

    }

    public void updateQues(){

        int upperBounds = 4;
        int lowerBounds = 1;
        int numbers = lowerBounds + (int)(Math.random() * ((upperBounds - lowerBounds) + 1));

        SvgLoader.pluck()
                .with(quiz1.this)
                .load(countryDatas.get(index).getFlag(), flagImage);

        if(numbers==1){
            optionA.setText(countryDatas.get(index).getName());
            optionB.setText(countryDatas.get(index+1).getName());
            optionC.setText(countryDatas.get(index+2).getName());
            optionD.setText(countryDatas.get(index+3).getName());
        }else if(numbers==2){
            optionA.setText(countryDatas.get(index+1).getName());
            optionB.setText(countryDatas.get(index).getName());
            optionC.setText(countryDatas.get(index+2).getName());
            optionD.setText(countryDatas.get(index+3).getName());
        }else if(numbers==3){
            optionA.setText(countryDatas.get(index+2).getName());
            optionB.setText(countryDatas.get(index+1).getName());
            optionC.setText(countryDatas.get(index).getName());
            optionD.setText(countryDatas.get(index+3).getName());
        }else if(numbers==4){
            optionA.setText(countryDatas.get(index+3).getName());
            optionB.setText(countryDatas.get(index+1).getName());
            optionC.setText(countryDatas.get(index+2).getName());
            optionD.setText(countryDatas.get(index).getName());
        }
    }
    public void retrieveJsonForCurrent(){
        Call<List<countryData>> call;
        call= QuizApi.getInstance().getApi().getCountries();

        call.enqueue(new Callback<List<countryData>>() {
            @Override
            public void onResponse(Call<List<countryData>> call, Response<List<countryData>> response) {
                // if (response.isSuccessful() && response.body().getArticles() != null){
                if (response.isSuccessful() && response.body() != null){
                    countryDatas.clear();
                    countryDatas = response.body();

                  //  adapter = new WeatherRecyclerAdapter(weather1.this,weatherList);
                  //  recyclerView.setAdapter(adapter);
                    int upperBound = 45;
                    int lowerBound = 1;
                    int number = lowerBound + (int)(Math.random() * ((upperBound - lowerBound) + 1));
                    String imageUrl = countryDatas.get(0).getFlag();

                    Collections.shuffle(countryDatas);
                    flagImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    SvgLoader.pluck()
                            .with(quiz1.this)
                            .load(countryDatas.get(index).getFlag(), flagImage);
                   //String yo = quizData.get(0).name;
                    String yo = countryDatas.get(49).getName();
                   // Sharp.loadString("https://restcountries.eu/data/mmr.svg").into(flagImage);

                    int upperBounds = 4;
                    int lowerBounds = 1;
                    int numbers = lowerBounds + (int)(Math.random() * ((upperBounds - lowerBounds) + 1));

                    if(numbers==1){
                        optionA.setText(countryDatas.get(index).getName());
                        optionB.setText(countryDatas.get(index+1).getName());
                        optionC.setText(countryDatas.get(index+2).getName());
                        optionD.setText(countryDatas.get(index+3).getName());
                    }else if(numbers==2){
                        optionA.setText(countryDatas.get(index+1).getName());
                        optionB.setText(countryDatas.get(index).getName());
                        optionC.setText(countryDatas.get(index+2).getName());
                        optionD.setText(countryDatas.get(index+3).getName());
                    }else if(numbers==3){
                        optionA.setText(countryDatas.get(index+2).getName());
                        optionB.setText(countryDatas.get(index+1).getName());
                        optionC.setText(countryDatas.get(index).getName());
                        optionD.setText(countryDatas.get(index+3).getName());
                    }else if(numbers==4){
                        optionA.setText(countryDatas.get(index+3).getName());
                        optionB.setText(countryDatas.get(index+1).getName());
                        optionC.setText(countryDatas.get(index+2).getName());
                        optionD.setText(countryDatas.get(index).getName());
                    }
                    quesMark.setVisibility(View.INVISIBLE);

                    //  int yo = response.body().getCnt();
                   // String v=response.body().getCod();
                    //  Log.d("yonju","yoyrd ");
                    // Log.d("Editable", v + yo+6);
                    Log.d("Editable", "ylylyly  "+numbers);
                    Log.d("Editable", "janin aaaaaa");
                }
            }

            @Override
            public void onFailure(Call<List<countryData>> call, Throwable t) {
                Log.d("Editable", t.getLocalizedMessage());
            }
        });
    }
    public void correct(){
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("numss", "ylylyly  "+index);
                correctCount++;
                if(index<9){
                    index++;
                    updateQues();
                }else{
                    gameWon();
                }
                timerValue=21;
                nn=20000;
                countDownTimer.cancel();
                countDownTimer.start();
                resetColor();
                enableButton();
            }
        });



    }
    public void wrong()
    {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("numss", "ylylyly  "+index);
                wrongCount++;
                if(index<9){
                    index++;
                    updateQues();
                }else{
                    gameWon();
                }
                timerValue=21;
                nn=20000;
                countDownTimer.cancel();
                countDownTimer.start();
                resetColor();
                enableButton();
            }
        });


    }
    public void gameWon()
    {
        Intent intent=new Intent(quiz1.this,quiz2.class);
        intent.putExtra("correct",correctCount);
        intent.putExtra("wrong",wrongCount);
        startActivity(intent);

    }
    public void enableButton(){
        optionA.setClickable(true);
        optionB.setClickable(true);
        optionC.setClickable(true);
        optionD.setClickable(true);
    }
    public void disableButoon(){
        optionA.setClickable(false);
        optionB.setClickable(false);
        optionC.setClickable(false);
        optionD.setClickable(false);
    }
    public void resetColor(){
        cardA.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.whites));
        cardB.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.whites));
        cardC.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.whites));
        cardD.setBackgroundColor( ContextCompat.getColor(quiz1.this, R.color.whites));
    }

}