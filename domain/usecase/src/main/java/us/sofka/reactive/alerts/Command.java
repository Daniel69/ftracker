package us.sofka.reactive.alerts;

import lombok.Getter;
import lombok.Setter;
import us.sofka.reactive.alerts.model.events.FoundAlertEvent;
import us.sofka.reactive.common.GenericCommand;
import us.sofka.reactive.generic.EventInfo;

@Getter @Setter
public class Command implements GenericCommand {
    public enum Action {CREATE_ALERT, ADD_PHONE, ADD_FINDER_NAME, ADD_ADDRESS, ADD_COORDINATES};

    private EventInfo eventInfo;
    private FoundAlertEvent.AlertInfo alertInfo;
    private Action action;

}
