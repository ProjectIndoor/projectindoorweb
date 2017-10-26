package de.hft_stuttgart.jpawarmup.parsers;

import de.hft_stuttgart.jpawarmup.entities.SensorData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParserHandler {

    private static ParserHandler theInstance;


    private AccelerometerDataParser accelerometerDataParser;
    private Map<ParserTypes, Parser> parsers;
    private ExecutorService executorService;

    private ParserHandler() {
    }

    public static ParserHandler getInstance() {
        if (theInstance == null) {
            theInstance = new ParserHandler();
            theInstance.parsers = new HashMap<>();
        }
        return theInstance;
    }

    public void initParsersForFile(String fileName) {

        parsers.put(ParserTypes.ACCELERATION_DATA, new AccelerometerDataParser(fileName));
        parsers.put(ParserTypes.MAGNETOMETER_DATA, new MagnetometerDataParser(fileName));
        parsers.put(ParserTypes.LIGHT_DATA, new LightDataParser(fileName));

    }

    public void runParsers() {

        executorService = Executors.newFixedThreadPool(parsers.size());
        for (ParserTypes type :
                parsers.keySet()) {

            executorService.execute(parsers.get(type));

        }

        try {
            executorService.shutdown();
            executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public List<? extends SensorData> collectParseResultsByType(ParserTypes type) {

        return parsers.get(type).getParsedData();


    }

    public List<ParserTypes> getAllKnownParsers(){
        return new ArrayList<>(parsers.keySet());
    }


}
