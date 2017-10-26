package de.hft_stuttgart.jpawarmup.parsers;

import de.hft_stuttgart.jpawarmup.entities.LightData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class LightDataParser extends Parser<LightData>{


    public LightDataParser(String fileToParse){
        super(fileToParse);
    }

    @Override
    protected boolean isDataValid(String source) {

        return !source.contains("%")
                && !source.isEmpty()
                && source.split(";")[0].equals("LIGH")
                && source.split(";").length == 5;
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
                 float light = Float.valueOf(elements[3]);
                 String dataUnit = "lux";
                 int accuracy = Integer.valueOf(elements[4]);

                 return new LightData(super.logFile.getId(), rawName, appTimestamp, sensorTimestamp, light, dataUnit, accuracy);


            }).collect(Collectors.toCollection(() -> super.parsed));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
