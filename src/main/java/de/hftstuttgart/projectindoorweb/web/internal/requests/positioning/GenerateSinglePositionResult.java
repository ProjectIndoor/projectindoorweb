package de.hftstuttgart.projectindoorweb.web.internal.requests.positioning;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerateSinglePositionResult {

    private String wifiReading;
    private Long[] radioMapFileIdentifiers;

    @JsonCreator
    public GenerateSinglePositionResult(@JsonProperty("wifiReading")String wifiReading,
                                        @JsonProperty("radioMapFileIdentifiers")Long[] radioMapFileIdentifiers) {
        this.wifiReading = wifiReading;
        this.radioMapFileIdentifiers = radioMapFileIdentifiers;
    }

    public String getWifiReading() {
        return wifiReading;
    }

    public void setWifiReading(String wifiReading) {
        this.wifiReading = wifiReading;
    }

    public Long[] getRadioMapFileIdentifiers() {
        return radioMapFileIdentifiers;
    }

    public void setRadioMapFileIdentifiers(Long[] radioMapFileIdentifiers) {
        this.radioMapFileIdentifiers = radioMapFileIdentifiers;
    }
}
