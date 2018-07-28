package us.sofka.reactive.repository.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Document(collection = "Alerts")
public class FoundAlertData {

    @Id
    private String id;
    private String petId;
    private Date creationDate;
    private String phone;
    private String finderName;
    private String placeId;
    private GeoJsonPoint location;
}
