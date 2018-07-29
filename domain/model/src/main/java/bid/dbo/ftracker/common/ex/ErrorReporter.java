package bid.dbo.ftracker.common.ex;

import reactor.core.publisher.Mono;

public interface ErrorReporter {
    default <T> Mono<T> dError(BusinessValidationException.Type type){
        return Mono.error(type.build());
    }
}
