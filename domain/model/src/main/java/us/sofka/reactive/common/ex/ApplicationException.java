package us.sofka.reactive.common.ex;

public class ApplicationException extends RuntimeException {

    private String code;

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode(){
        return code;
    }
}
