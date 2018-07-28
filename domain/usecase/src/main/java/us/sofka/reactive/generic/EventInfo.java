package us.sofka.reactive.generic;

import java.util.LinkedHashMap;
import java.util.Map;

public class EventInfo {

    private String sourceIp;
    private String browser;
    private String device;
    private String os;
    private String extraData;
    private Map<String, String> properties;

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }


    public String getSourceIp() {
        return sourceIp;
    }

    public String getBrowser() {
        return browser;
    }

    public String getDevice() {
        return device;
    }

    public String getOs() {
        return os;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Map<String, String> getProperties() {
        return properties != null ? properties : new LinkedHashMap<>();
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
