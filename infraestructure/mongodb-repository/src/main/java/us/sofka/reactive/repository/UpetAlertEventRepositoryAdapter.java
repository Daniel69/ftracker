package us.sofka.reactive.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import us.sofka.reactive.alerts.model.events.FoundAlertEvent;
import us.sofka.reactive.alerts.repository.FoundAlertEventRepository;
import us.sofka.reactive.repository.data.FoundAlertEventData;
import us.sofka.reactive.repository.data.interfaces.FoundAlertEventDataRepository;
import us.sofka.reactive.repository.generic.AdapterOperations;
import us.sofka.reactive.mapper.ObjectMapper;

@Repository("alertEventRepository")
public class UpetAlertEventRepositoryAdapter extends AdapterOperations<FoundAlertEvent, FoundAlertEventData, String, FoundAlertEventDataRepository> implements FoundAlertEventRepository {

    @Autowired
    public UpetAlertEventRepositoryAdapter(FoundAlertEventDataRepository repository, ObjectMapper mapper) {
        super(repository, mapper, FoundAlertEventData.class);
    }

    @Override
    protected FoundAlertEvent toEntity(FoundAlertEventData data) {
        return mapper.mapBuilder(data, FoundAlertEvent.FoundAlertEventBuilder.class).build();
    }
}
