package de.hft_stuttgart.jpawarmup.parsers;

import de.hft_stuttgart.jpawarmup.entities.MagnetometerData;
import de.hft_stuttgart.jpawarmup.entities.SensorData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class MagnetometerDataParser extends Parser<SensorData> {



    public MagnetometerDataParser(String fileToParse) {
        super(fileToParse);
    }


    @Override
    public void run() {

        try {
            Files.readAllLines(Paths.get(super.logFile.getSourceFileName())).stream().filter(s -> {

                return !s.contains("%")
                        && !s.isEmpty()
                        && s.split(";")[0].equals("MAGN")
                        && isDataValid(s);

            }).map(s -> {

                String[] elements = s.split(";");
                String rawName = elements[0];
                int appTimestamp = Integer.valueOf(elements[1].replace(".", ""));
                int sensorTimestamp = Integer.valueOf(elements[2].replace(".", ""));
                float magnX = Float.valueOf(elements[3]);
                float magnY = Float.valueOf(elements[4]);
                float magnZ = Float.valueOf(elements[5]);
                String dataUnit = "uT";
                int accuracy = Integer.valueOf(elements[6]);

                return new MagnetometerData(super.logFile.getId(), rawName, appTimestamp, sensorTimestamp, magnX, magnY, magnZ, dataUnit, accuracy);

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
