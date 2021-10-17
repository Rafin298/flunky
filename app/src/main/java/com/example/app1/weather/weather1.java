package com.example.app1.weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.app1.Dashboard;
import com.example.app1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import model.weather.ForecastData;
import model.weather.Weather;
import model.weather.WeatherList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ui.WeatherRecyclerAdapter;
import util.WeatherForecastApi;

public class weather1 extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    LottieAnimationView weatherIcon;
    ImageButton backss;



    final String myApiKey="31b722db82b7ee077703730297e08927";
    final String weatherUrl="https://api.openweathermap.org/data/2.5/weather";
   // final String weatherForecastUrl="http://api.openweathermap.org/data/2.5/forecast/hourly?id=524901&lang=zh_cn&appid={API key}";
 int REQUEST_CODES=2;
    final long minTime = 5000;
    final float minDistance = 1000;
    final int request_Code = 101;

    String Location_Provider = LocationManager.NETWORK_PROVIDER; //manisfest e gps use korsi tai

    WeatherRecyclerAdapter adapter;
    RecyclerView recyclerView;

    TextView nameOfCity, weatherState, temperature;
   // ImageView weatherIcon;

    Button cityFinder;
    Button homeButton,signOutButton;

    LocationManager locationManager;
    LocationListener locationListner;
//t
    List<WeatherList> weatherList = new ArrayList<>();
    List<Weather> weather = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather1);

        weatherState = findViewById(R.id.weatherState);
        temperature = findViewById(R.id.temperature);
        weatherIcon = findViewById(R.id.logon);
        cityFinder = findViewById(R.id.cityFinder);
        nameOfCity = findViewById(R.id.nameOfCity);
        recyclerView = findViewById(R.id.weatherRecycleView);
        backss= findViewById(R.id.backss);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        backss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(weather1.this, Dashboard.class));
            }
        });


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);


        Log.d("myTag", "This is my message");
        cityFinder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(weather1.this, weatherSearch.class);
               // startActivity(intent);
                 startActivityForResult(intent,REQUEST_CODES);
                Log.d("myTag", "This is my message");
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getWeatherForCurrentLocation();
//    }
    String city;
    int nn;
@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == REQUEST_CODES) {
        assert data != null;
        city = data.getStringExtra("City");
       // nn = data.getIntExtra("mm",0);

    }


}
    @Override
    protected void onResume() {
        super.onResume();
       Log.d("MyApp","I am here" );
//        Intent mIntent=getIntent();
//        String city= mIntent.getStringExtra("City");
//        Log.d("MyApp","city "+city);
        if(city!=null)
        {
            getWeatherForNewCity(city);
        }
        else
        {
            getWeatherForCurrentLocation();
        }


    }
    private void getWeatherForNewCity(String city)
    {
        RequestParams params=new RequestParams();
        params.put("q",city);
        params.put("appid",myApiKey);
        letsdoSomeNetworking(params);
        retrieveJsonForCurrent(city,city,myApiKey);

    }

    private void getWeatherForCurrentLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);//provides the object of current location
        locationListner = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String Latitude = String.valueOf(location.getLatitude());
                String Longitude = String.valueOf(location.getLongitude());

                Log.d("lat",Latitude);
                Log.d("lat",Longitude );


                RequestParams params = new RequestParams();
                params.put("lat", Latitude);
                params.put("lon", Longitude);
                params.put("appid", myApiKey);
                retrieveJsonForCurrent(Latitude,Longitude,myApiKey);
                letsdoSomeNetworking(params);
                Log.d("Editable", "append: " + 55);
               // retrieveJson(Latitude,Longitude,myApiKey);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                //not able to get location
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            // ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},request_Code);
            return;
        }

        locationManager.requestLocationUpdates(Location_Provider, minTime, minDistance, locationListner);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode==request_Code)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)//if user allow the location
            {
                Toast.makeText(weather1.this,"Locationget Succesffully",Toast.LENGTH_SHORT).show();
                getWeatherForCurrentLocation();
            }
            else
            {
                //user denied the permission
            }
        }


    }


    private  void letsdoSomeNetworking(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(weatherUrl,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText(weather1.this,"Data Get Success",Toast.LENGTH_SHORT).show();

                weatherData weatherD=weatherData.fromJson(response);
                updateUI(weatherD);
                // super.onSuccess(statusCode, headers, response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
    public void retrieveJsonForCurrent(String lat ,String lon, String appid){
        Call<ForecastData> call;
        if(city!=null)
        {
            call= WeatherForecastApi.getInstance().getApi().getCityData(city,appid);
        }
        else
        {
            call= WeatherForecastApi.getInstance().getApi().getForecastedData(lat,lon,appid);
        }

           // call= WeatherForecastApi.getInstance().getApi().getForecastedData(lat,lon,appid);

        // call= NewsApiClient.getInstance().getApi().getHeadlines(country,apiKey);


        call.enqueue(new Callback<ForecastData>() {
            @Override
            public void onResponse(Call<ForecastData> call, Response<ForecastData> response) {
               // if (response.isSuccessful() && response.body().getArticles() != null){
                    if (response.isSuccessful() && response.body() != null){
                        weatherList.clear();
                        weatherList = response.body().getWeatherList();

                        adapter = new WeatherRecyclerAdapter(weather1.this,weatherList);
                        recyclerView.setAdapter(adapter);

                    String yo = weatherList.get(0).weather.get(0).main;
                      //  int yo = response.body().getCnt();
                    String v=response.body().getCod();
                      //  Log.d("yonju","yoyrd ");
                       // Log.d("Editable", v + yo+6);
                        Log.d("Editable", yo);


                }
            }

            @Override
            public void onFailure(Call<ForecastData> call, Throwable t) {
                Log.d("Editable", t.getLocalizedMessage());
            }
        });
    }


    private  void updateUI(weatherData weather){


        temperature.setText(weather.getmTemperature());
        nameOfCity.setText(weather.getMcity());
        weatherState.setText(weather.getmWeatherType());
        int resourceID=getResources().getIdentifier(weather.getMicon(),"raw",getPackageName());
        weatherIcon.setAnimation(resourceID);


    }
    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager!=null)
        {
            locationManager.removeUpdates(locationListner);
        }
    }
}