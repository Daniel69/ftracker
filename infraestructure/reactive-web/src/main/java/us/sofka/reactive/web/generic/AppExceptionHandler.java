package us.sofka.reactive.web.generic;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import us.sofka.reactive.common.ex.ApplicationException;

@Component
public class AppExceptionHandler implements ErrorWebExceptionHandler {
    private static final String ERROR_GENERICO = "Ooops! Something went wrong.";

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString;
//        ex.printStackTrace();
        if (ex instanceof ApplicationException) {
            try {
                System.err.println(((ApplicationException) ex).getCode());
                System.err.println(((ApplicationException) ex).getMessage());
                jsonInString = mapper.writeValueAsString(new CommandResult(false, ((ApplicationException) ex).getCode(), ex.getMessage()));
            } catch (Exception e) {
                e.printStackTrace();
                return Mono.empty();
            }finally {
                exchange.getResponse().setStatusCode(HttpStatus.PRECONDITION_FAILED);
            }
        } else {
            try {
                ex.printStackTrace();
                jsonInString = mapper.writeValueAsString(new CommandResult(false, "500", ERROR_GENERICO));
            } catch (Exception e) {
                e.printStackTrace();
                return Mono.empty();
            }finally {
                exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        exchange.getResponse().getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json");
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().allocateBuffer().write(jsonInString.getBytes())));
    }

    private String buildMessage(ServerHttpRequest request, Throwable ex) {
        return "Failed to handle request [" + request.getMethod() + " "
            + request.getURI() + "]: " + ex.getMessage();
    }
}
