package com.eilco.meteo;

public class Hours {

    private int Temperature;
    private String urlImg;
    private String Time;

    public Hours(int temperature, String urlImg, String time) {
        Temperature = temperature;
        this.urlImg = urlImg;
        Time = time;
    }

    public int getTemperature() {
        return Temperature;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public String getTime() {
        return Time;
    }

    public void setTemperature(int temperature) {
        Temperature = temperature;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public void setTime(String time) {
        Time = time;
    }

    @Override
    public String toString() {
        return "Hours{" +
                "Temperature=" + Temperature +
                ", urlImg='" + urlImg + '\'' +
                ", Time='" + Time + '\'' +
                '}';
    }
}
