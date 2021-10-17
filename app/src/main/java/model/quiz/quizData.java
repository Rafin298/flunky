package model.quiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import model.weather.Weather;

public class quizData {
    @SerializedName("")
    @Expose
    public List<countryData> countryData;

    public List<countryData> getCountryData() {
        return countryData;
    }

    public void setCountryData(List<countryData> countryData) {
        this.countryData = countryData;
    }
}
