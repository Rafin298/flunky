package util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherForecastApi {
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static WeatherForecastApi weatherForecastApi;
    private static Retrofit retrofit;


    private WeatherForecastApi(){

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized WeatherForecastApi getInstance(){
        if (weatherForecastApi == null){
            weatherForecastApi = new WeatherForecastApi();
        }
        return weatherForecastApi;
    }


    public weatherApiInterface getApi(){
        return retrofit.create(weatherApiInterface.class);
    }
}
