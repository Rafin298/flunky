package com.example.app1.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.app1.Dashboard;
import com.example.app1.R;

public class weatherSearch extends AppCompatActivity {
    ImageButton backss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_search);

        EditText editText=findViewById(R.id.cityName);
        backss=findViewById(R.id.backss);
        Button searchButton=findViewById(R.id.searchButton);
        backss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(weatherSearch.this, weather1.class));

            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity= editText.getText().toString();
              //  int num=5;
                Intent intent=new Intent(weatherSearch.this,weather1.class);
                intent.putExtra("City",newCity);
               // intent.putExtra("mm",num);
                setResult(RESULT_OK, intent);
              // startActivity(intent);
                finish();
            }
        });

//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                String newCity= editText.getText().toString();
//                Intent intent=new Intent(weatherSearch.this,weather1.class);
//                intent.putExtra("City",newCity);
//                startActivity(intent);
//
//
//
//                return false;
//
//            }
//        });
    }
}