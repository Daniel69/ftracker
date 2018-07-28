package us.sofka.reactive.alerts;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import us.sofka.reactive.alerts.model.FoundAlert;
import us.sofka.reactive.alerts.model.UpetAlertFactory;
import us.sofka.reactive.alerts.model.UpetAlertOperations;
import us.sofka.reactive.alerts.model.events.EventType;
import us.sofka.reactive.alerts.model.events.EventsFactory;
import us.sofka.reactive.alerts.model.events.FoundAlertEvent;
import us.sofka.reactive.alerts.repository.FoundAlertEventRepository;
import us.sofka.reactive.alerts.repository.FoundAlertRepository;
import us.sofka.reactive.common.UniqueIDGenerator;
import us.sofka.reactive.common.ex.BusinessValidationException;
import us.sofka.reactive.mapper.ObjectMapper;

import java.util.Date;
import java.util.function.Function;

import static reactor.core.publisher.Mono.*;
import static reactor.function.TupleUtils.function;

@AllArgsConstructor
public class CreateAlertUseCase implements UpetAlertFactory, UpetAlertOperations, UniqueIDGenerator, EventsFactory {

    private FoundAlertRepository alerts;
    private FoundAlertEventRepository events;
    private ObjectMapper mapper;

    public Mono<FoundAlertEvent> createAlert(final Command command) {
        final FoundAlertEvent.AlertInfo alertInfo = command.getAlertInfo();
        return zip(now(), uuid())
            .map(function((now, uuid) -> createAlert(uuid, now, alertInfo.getPetId())))
            .flatMap(alert -> {
                Mono<FoundAlertEvent> event = uuid().flatMap(uuid -> createEvent(alert, uuid, command, EventType.ALERT_CREATED));
                return zip(event, just(alert));
            })
            .flatMap(function((event, alert) -> zip(alerts.save(alert), events.save(event)).map(t -> t.getT2())));

    }

    public Mono<FoundAlertEvent> addPhone(Command command) {
        return addInfo(info -> addPhone(info.getPhone()), command, EventType.PHONE_ADDED);
    }

    public Mono<FoundAlertEvent> addFinderName(Command command) {
        return addInfo(info -> addFinderName(info.getFinderName()), command, EventType.FINDER_NAME_ADDED);
    }

    public Mono<FoundAlertEvent> addPlaceId(Command command) {
        return addInfo(info -> addPlaceId(info.getPlaceId()), command, EventType.LOCATION_ADDED);
    }

    public Mono<FoundAlertEvent> addCoordinates(Command command) {
        return addInfo(i -> addLatitudeAndLongitude(decimal(i.getLatitude()), decimal(i.getLongitude())),
            command, EventType.COORDINATES_ADDED);
    }

    private Double decimal(String val) {
        return Double.parseDouble(val);
    }

    private Mono<FoundAlertEvent> addInfo(Function<FoundAlertEvent.AlertInfo, Function<FoundAlert, FoundAlert>> addFn, Command command, EventType type) {
        final FoundAlertEvent.AlertInfo alertInfo = command.getAlertInfo();
        final Function<FoundAlert, FoundAlert> alertMutator = addFn.apply(alertInfo);
        return alerts.findById(alertInfo.getAlertId()).switchIfEmpty(dfError(BusinessValidationException.Type.INVALID_ALERT_INITIAL_DATA))
            .flatMap(originAlert ->
                just(originAlert)
                    .map(alertMutator)
                    .zipWith(uuid())
                    .flatMap(function((newAlert, uuid) ->
                        alerts.save(newAlert)
                            .then(createEvent(newAlert, uuid, command, type)
                                .flatMap(events::save)
                                .onErrorResume(ex -> alerts.save(originAlert).then(error(ex))))
                    )));
    }

    private <T> Mono<T> dfError(BusinessValidationException.Type type) {
        return defer(() -> error(type.build()));
    }

    private Mono<FoundAlertEvent> createEvent(FoundAlert alert, String uuid, Command command, EventType type) {
        return create(() -> {
            FoundAlertEvent.FoundAlertEventBuilder eventBuilder = mapper.mapBuilder(command.getEventInfo(), FoundAlertEvent.FoundAlertEventBuilder.class);
            eventBuilder.eventType(type);
            eventBuilder.id(uuid);
            eventBuilder.info(command.getAlertInfo());
            eventBuilder.petId(alert.getPetId());
            eventBuilder.eventDate(new Date());
            eventBuilder.alertId(alert.getId());
            return eventBuilder.build();
        });
    }

}
