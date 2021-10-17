package model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherList {
    @SerializedName("main")
    @Expose
    public ListMain listMain;

    @SerializedName("weather")
    @Expose
    public List<Weather> weather;

    @SerializedName("dt_txt")
    @Expose
    public String dt_txt;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    @SerializedName("dt")
    @Expose
    public int dt;

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public ListMain getListMain() {
        return listMain;
    }

    public void setListMain(ListMain listMain) {
        this.listMain = listMain;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
