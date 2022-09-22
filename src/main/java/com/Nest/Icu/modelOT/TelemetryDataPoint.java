package com.Nest.Icu.modelOT;

import com.google.gson.Gson;

import lombok.Data;

@Data
public class TelemetryDataPoint {

    // public double temperature;
    // public double humidity;
    public int heartbeat;
    public int systolePressure;
    public int diastolePressure;


    // Serialize object to JSON format.
    public String serialize() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }



}
