package de.hft_stuttgart.projectindoorweb.parser.internal;

public class LightDataParser extends SensorDataParser{
    public LightDataParser(String filePath) {
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
