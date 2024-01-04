package com.eilco.meteo;

public class Week {

    private int Temperature;
    private String urlimg;
    private String Date;
    private String Detail;

    public Week(int temperature, String urlimg, String date, String detail) {
        Temperature = temperature;
        this.urlimg = urlimg;
        Date = date;
        Detail = detail;
    }

    public int getTemperature() {
        return Temperature;
    }

    public String getUrlimg() {
        return urlimg;
    }

    public String getDate() {
        return Date;
    }

    public String getDetail() {
        return Detail;
    }

    public void setTemperature(int temperature) {
        Temperature = temperature;
    }

    public void setUrlimg(String urlimg) {
        this.urlimg = urlimg;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    @Override
    public String toString() {
        return "Week{" +
                "Temperature=" + Temperature +
                ", urlimg='" + urlimg + '\'' +
                ", Date='" + Date + '\'' +
                ", Detail='" + Detail + '\'' +
                '}';
    }
}
