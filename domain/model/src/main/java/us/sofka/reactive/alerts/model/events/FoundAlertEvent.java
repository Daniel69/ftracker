package us.sofka.reactive.alerts.model.events;

import lombok.*;

import java.util.Date;
import java.util.Map;

@Getter
@Builder(toBuilder = true)
public class FoundAlertEvent {

    private final String id;
    private final Date eventDate;
    private final EventType eventType;
    private final Long userId;
    private final String userEmail;
    private final String upetID;
    private final String alertId;
    private final String sourceIp;
    private final String browser;
    private final String device;
    private final String os;
    private final String extraData;
    private final String petId;
    private final AlertInfo info;

    @Getter @Setter
    public static class AlertInfo {
        private String alertId;
        private String petId;
        private String phone;
        private String finderName;
        private String latitude;
        private String longitude;
        private String placeId;
    }


    @Data
    public static class SimpleEvent {
        private EventType eventType;
        private String alertId;
        private Long petId;
    }
}
