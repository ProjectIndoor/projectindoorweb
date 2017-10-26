package de.hft_stuttgart.jpawarmup.parsers;

import de.hft_stuttgart.jpawarmup.entities.AccelerometerData;
import de.hft_stuttgart.jpawarmup.entities.SensorData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class AccelerometerDataParser extends Parser<SensorData> {



    public AccelerometerDataParser(String fileToParse){
        super(fileToParse);
    }

    @Override
    protected boolean isDataValid(String source) {

        return !source.contains("%")
                && !source.isEmpty()
                && source.split(";")[0].equals("ACCE")
                && source.split(";").length == 7;

    }


    @Override
    public void run() {

        try {
            Files.readAllLines(Paths.get(super.logFile.getSourceFileName())).stream().filter(s -> {

                return isDataValid(s);

            }).map(s -> {

                String[] elements = s.split(";");
                String rawName = elements[0];
                long appTimestamp = Long.valueOf(elements[1].replace(".", ""));
                long sensorTimestamp = Long.valueOf(elements[2].replace(".", ""));
                float accX = Float.valueOf(elements[3]);
                float accY = Float.valueOf(elements[4]);
                float accZ = Float.valueOf(elements[5]);
                String dataUnit = "m/s^²";
                int accuracy = Integer.valueOf(elements[6]);

                return new AccelerometerData(super.logFile.getId(), rawName, appTimestamp, sensorTimestamp, accX, accY, accZ, dataUnit, accuracy);


            }).collect(Collectors.toCollection(() -> super.parsed));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
