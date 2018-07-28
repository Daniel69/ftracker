package us.sofka.reactive.repository.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import us.sofka.reactive.alerts.model.events.EventType;
import us.sofka.reactive.alerts.model.events.FoundAlertEvent;

import java.util.Date;
import java.util.Map;

@Data
@Document(collection = "AlertEvents")
public class FoundAlertEventData {

    @Id
    private String id;
    private Date eventDate;
    private EventType eventType;
    private Long userId;
    private String userEmail;
    private String upetID;
    private String alertId;
    private String sourceIp;
    private String browser;
    private String device;
    private String os;
    private Map<String, String> properties;
    private String extraData;
    private Long petId;
    private FoundAlertEvent.AlertInfo info;
}
