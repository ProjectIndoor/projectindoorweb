package de.hft_stuttgart.projectindoorweb.parser.internal;

public class GyroscopeDataParser extends  SensorDataParser{
    public GyroscopeDataParser(String filePath) {
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
