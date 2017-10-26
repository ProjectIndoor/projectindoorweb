package de.hft_stuttgart.jpawarmup.parsers;

import de.hft_stuttgart.jpawarmup.entities.GnssData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class GnssDataParser extends Parser<GnssData>{


    public GnssDataParser(String fileToParse){
        super(fileToParse);
    }



    @Override
    protected boolean isDataValid(String source) {
        return !source.contains("%")
                && !source.isEmpty()
                && source.split(";")[0].equals("GNSS")
                && source.split(";").length == 11;
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
                float latitude = Float.valueOf(elements[3]);
                float longitude = Float.valueOf(elements[4]);
                String dataUnitLatLong = "deg";
                float altitude = Float.valueOf(elements[5]);
                String dataUnitAlt = "m";
                float bearing = Float.valueOf(elements[6]);
                String dataUnitBearing = "deg";
                float accuracy = Float.valueOf(elements[7]);
                String dataUnitAcc = "m";
                float speed = Float.valueOf(elements[8]);
                String dataUnitSpeed = "m/s";
                int satInView = Integer.valueOf(elements[9]);
                int satInUse = Integer.valueOf(elements[10]);

                return new GnssData(super.logFile.getId(), rawName, appTimestamp, sensorTimestamp, latitude, longitude, dataUnitLatLong, altitude,
                        dataUnitAlt, bearing, dataUnitBearing, accuracy, dataUnitAcc, speed, dataUnitSpeed, satInView, satInUse);


            }).collect(Collectors.toCollection(() -> super.parsed));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
