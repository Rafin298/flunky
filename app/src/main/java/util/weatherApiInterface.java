package util;

import model.weather.ForecastData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weatherApiInterface {
    @GET("forecast")
    Call<ForecastData> getForecastedData(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("appid") String appid

    );
    @GET("forecast")
    Call<ForecastData> getCityData(
            @Query("q") String q,
            @Query("appid") String appid

    );
}
