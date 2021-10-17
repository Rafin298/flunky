package model.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListMain {
    @SerializedName("temp")
    @Expose
    public double temp;
    @SerializedName("temp_min")
    @Expose
    public double temp_min;

    @SerializedName("temp_max")
    @Expose
    public double temp_max;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }
}
