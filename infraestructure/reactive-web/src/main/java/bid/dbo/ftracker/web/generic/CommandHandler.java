package bid.dbo.ftracker.web.generic;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import bid.dbo.ftracker.common.GenericCommand;
import bid.dbo.ftracker.common.ex.ApplicationException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class CommandHandler<Command extends GenericCommand, Event> {


    private Map<Enum<?>, Function<Command, Mono<Event>>> handlers = new LinkedHashMap<>();

    @PostMapping(path = "command/{commandCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Event> handleCommand(@RequestBody Command command, @PathVariable("commandCode") String commandCode){
        if(command.getAction().name().equals(commandCode))
            return doHandle(command);
        else
            throw new ApplicationException("Invalid Command name", "INVALID_COMMAND_NAME");
    }

    @PostMapping(path = "commands", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Event> handleCommands(@RequestBody Flux<Command> commands){
        return commands.flatMap(this::doHandle);
    }


    private Mono<Event> doHandle(Command command){
        return handlers.get(command.getAction()).apply(command);
    }

    protected void handle(Enum<?> action, Function<Command, Mono<Event>> fn){
        handlers.put(action, fn);
    }

}
