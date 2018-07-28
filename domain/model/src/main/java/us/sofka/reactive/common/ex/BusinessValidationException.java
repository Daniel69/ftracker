package us.sofka.reactive.common.ex;

public class BusinessValidationException extends ApplicationException {

    public enum Type {
        INVALID_ALERT_INITIAL_DATA("Invalid AlertEvent initial data");

        private final String message;

        public BusinessValidationException build() {
            return new BusinessValidationException(this);
        }

        Type(String message) {
            this.message = message;
        }

    }

    private final Type type;

    public BusinessValidationException(Type type){
        super(type.message);
        this.type = type;
    }

    @Override
    public String getCode(){
        return type.name();
    }


}
