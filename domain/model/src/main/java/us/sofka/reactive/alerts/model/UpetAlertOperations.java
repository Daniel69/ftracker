package us.sofka.reactive.alerts.model;

import java.util.function.Function;

public interface UpetAlertOperations {

    default Function<FoundAlert, FoundAlert> addPhone(String phone){
        return foundAlert -> foundAlert.toBuilder().phone(phone).build();
    }

    default Function<FoundAlert, FoundAlert> addFinderName(String finderName){
        return foundAlert -> foundAlert.toBuilder().finderName(finderName).build();
    }

    default Function<FoundAlert, FoundAlert> addPlaceId(String placeId){
        return foundAlert -> foundAlert.toBuilder().placeId(placeId).build();
    }

    default Function<FoundAlert, FoundAlert> addLatitudeAndLongitude(Double latitude, Double longitude){
        return foundAlert -> foundAlert.toBuilder().latitude(latitude).longitude(longitude).build();
    }

}


