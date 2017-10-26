package de.hft_stuttgart.jpawarmup.parsers;

import de.hft_stuttgart.jpawarmup.entities.AccelerometerData;
import de.hft_stuttgart.jpawarmup.entities.SensorData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class AccelerometerDataParser extends Parser<SensorData> {

    private String fileToParse;


    public AccelerometerDataParser(String fileToParse) {
        this.fileToParse = fileToParse;
    }


    @Override
    public void run() {

        try {
            Files.readAllLines(Paths.get(this.fileToParse)).stream().filter(s -> {

                return !s.contains("%") && !s.isEmpty() && s.split(";")[0].equals("ACCE") && isDataValid(s);

            }).map(s -> {

                String[] elements = s.split(";");
                String rawName = elements[0];
                int appTimestamp = Integer.valueOf(elements[1].replace(".", ""));
                int sensorTimestamp = Integer.valueOf(elements[2].replace(".", ""));
                float accX = Float.valueOf(elements[3]);
                float accY = Float.valueOf(elements[4]);
                float accZ = Float.valueOf(elements[5]);
                String dataUnit = "m/s^²";
                int accuracy = Integer.valueOf(elements[6]);

                return new AccelerometerData(rawName, appTimestamp, sensorTimestamp, accX, accY, accZ, dataUnit, accuracy);


            }).collect(Collectors.toCollection(() -> super.parsed));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected boolean isDataValid(String source) {

        return source.split(";").length == 7;

    }

}
