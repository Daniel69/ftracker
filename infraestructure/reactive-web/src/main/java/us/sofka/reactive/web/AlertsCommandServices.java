package us.sofka.reactive.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import us.sofka.reactive.alerts.Command;
import us.sofka.reactive.alerts.CreateAlertUseCase;
import us.sofka.reactive.alerts.model.events.FoundAlertEvent;
import us.sofka.reactive.web.generic.CommandHandler;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("alert/")
public class AlertsCommandServices extends CommandHandler<Command, FoundAlertEvent> {

    @Autowired
    private CreateAlertUseCase useCase;

    @PostConstruct
    void init(){
        handle(Command.Action.CREATE_ALERT, useCase::createAlert);
        handle(Command.Action.ADD_ADDRESS, useCase::addPlaceId);
        handle(Command.Action.ADD_FINDER_NAME, useCase::addFinderName);
        handle(Command.Action.ADD_COORDINATES, useCase::addCoordinates);
        handle(Command.Action.ADD_PHONE, useCase::addPhone);
    }

}
