package us.sofka.reactive.alerts.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder(toBuilder = true)
public class FoundAlert {

    private final String id;
    private final String upetId;
    private final String petId;
    private final Date creationDate;
    private final String phone;
    private final String finderName;
    private final Double latitude;
    private final Double longitude;
    private final String placeId;

}


