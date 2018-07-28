package us.sofka.reactive.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Repository;
import us.sofka.reactive.alerts.model.FoundAlert;
import us.sofka.reactive.alerts.repository.FoundAlertRepository;
import us.sofka.reactive.repository.data.FoundAlertData;
import us.sofka.reactive.repository.data.interfaces.FoundAlertDataRepository;
import us.sofka.reactive.repository.generic.AdapterOperations;
import us.sofka.reactive.mapper.ObjectMapper;

import static java.util.Optional.ofNullable;

@Repository("alertRepository")
public class UpetAlertRepositoryAdapter extends AdapterOperations<FoundAlert, FoundAlertData, String, FoundAlertDataRepository> implements FoundAlertRepository {

    @Autowired
    public UpetAlertRepositoryAdapter(FoundAlertDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, FoundAlertData.class);
    }

    @Override
    protected FoundAlert toEntity(FoundAlertData data) {
        return mapper.mapBuilder(data, FoundAlert.FoundAlertBuilder.class)
            .latitude(ofNullable(data.getLocation()).map(Point::getY).orElse(null))
            .longitude(ofNullable(data.getLocation()).map(Point::getX).orElse(null))
            .build();
    }

    @Override
    protected FoundAlertData toData(FoundAlert alert) {
        FoundAlertData data = super.toData(alert);
        if(alert.getLatitude() != null && alert.getLongitude() != null){
            data.setLocation(new GeoJsonPoint(alert.getLongitude(), alert.getLatitude()));
        }
        return data;
    }
}
