package us.sofka.reactive.alerts.model;

import java.util.Date;

public interface UpetAlertFactory {

    default FoundAlert createAlert(String id, Date creationDate, String petId){
        return FoundAlert.builder().id(id)
                .creationDate(creationDate)
                .petId(petId)
                .build();
    }
}
