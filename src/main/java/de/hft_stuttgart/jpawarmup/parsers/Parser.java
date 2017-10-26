package de.hft_stuttgart.jpawarmup.parsers;

import de.hft_stuttgart.jpawarmup.entities.LocalFile;
import de.hft_stuttgart.jpawarmup.entities.SensorData;
import de.hft_stuttgart.jpawarmup.helpers.LocalFileHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class Parser<T> implements Runnable{

    protected List<SensorData> parsed;
    protected LocalFile logFile;

    protected Parser(String fileToParse){

        this.logFile = LocalFileHelper.getInstance().retrieveLogFileFromSource(fileToParse);
        this.parsed = new ArrayList<>();


    }

    public List<SensorData> getParsedData(){
        return parsed;
    }

    protected abstract boolean isDataValid(String source);


}
