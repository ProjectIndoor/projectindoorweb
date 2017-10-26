package de.hft_stuttgart.jpawarmup.parsers;

import de.hft_stuttgart.jpawarmup.entities.SensorData;

import java.util.ArrayList;
import java.util.List;

public abstract class Parser<T> implements Runnable{

    protected List<SensorData> parsed;

    protected Parser(){
        this.parsed = new ArrayList<>();
    }

    public List<SensorData> getParsedData(){
        return parsed;
    }

    protected abstract boolean isDataValid(String source);


}
