package de.hft_stuttgart.jpawarmup.parsers;

import de.hft_stuttgart.jpawarmup.entities.LightData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class LightDataParser extends Parser<LightData>{

    private final String fileToParse;

    public LightDataParser(String fileToParse){
        this.fileToParse = fileToParse;
    }

    @Override
    protected boolean isDataValid(String source) {
        return source.split(";").length == 5;
    }

    @Override
    public void run() {

        try {
            Files.readAllLines(Paths.get(fileToParse)).stream().filter(s -> {

                return !s.contains("%")
                        && !s.isEmpty()
                        && s.split(";")[0].equals("LIGH")
                        && isDataValid(s);

            }).map(s -> {

                 String[] elements = s.split(";");
                 String rawName = elements[0];
                 int appTimestamp = Integer.valueOf(elements[1].replace(".", ""));
                 int sensorTimestamp = Integer.valueOf(elements[2].replace(".", ""));
                 float light = Float.valueOf(elements[3]);
                 String dataUnit = "lux";
                 int accuracy = Integer.valueOf(elements[4]);

                 return new LightData(rawName, appTimestamp, sensorTimestamp, light, dataUnit, accuracy);


            }).collect(Collectors.toCollection(() -> super.parsed));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
