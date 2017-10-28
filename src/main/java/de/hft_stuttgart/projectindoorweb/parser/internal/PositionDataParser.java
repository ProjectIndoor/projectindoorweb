package de.hft_stuttgart.projectindoorweb.parser.internal;

public class PositionDataParser extends SensorDataParser {

    public PositionDataParser(String filePath) {
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
