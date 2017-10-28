package de.hft_stuttgart.projectindoorweb.parser.internal;

public class MagnetometerDataParser extends SensorDataParser {
    public MagnetometerDataParser(String filePath) {
        super(filePath);
    }

    @Override
    public void run() {

    }

    @Override
    protected boolean isValidLine(String line) {
        return false;
    }

}
