package us.sofka.reactive.alerts.model.events;

import reactor.core.publisher.Mono;
import us.sofka.reactive.common.StringUtil;
import us.sofka.reactive.common.ex.BusinessValidationException;

import java.util.function.Function;
import java.util.function.Supplier;

import static reactor.core.publisher.Mono.*;


public interface EventsFactory {

    default Mono<FoundAlertEvent> create(Supplier<FoundAlertEvent> builder){
        final FoundAlertEvent event = builder.get();
        return just(event).filter(this::isValid).switchIfEmpty(defer(() -> error(BusinessValidationException.Type.INVALID_ALERT_INITIAL_DATA.build())));
    }

    default boolean isValid(FoundAlertEvent event){
        return !StringUtil.isEmpty(event.getId())
                && !StringUtil.isEmpty(event.getAlertId())
                && event.getEventType() != null
                && event.getEventDate() != null;

    }

}
