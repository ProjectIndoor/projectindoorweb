package de.hft_stuttgart.projectindoorweb.parser.internal;

public class AccelerationDataParser extends SensorDataParser {

    public AccelerationDataParser(String filePath) {
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
