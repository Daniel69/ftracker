package bid.dbo.ftracker.common.ex;

public class BusinessValidationException extends ApplicationException {

    public enum Type {
        INVALID_ALERT_INITIAL_DATA("Invalid AlertEvent initial data"),
        INVALID_USER_INITIAL_DATA("Invalid User initial data"),
        INVALID_USERACCOUNT_INITIAL_DATA("Invalid User Account initial data"),
        INVALID_CATEGORY_INITIAL_DATA("Invalid Category initial data"),
        INVALID_ACCOUNT_INITIAL_DATA("Invalid Account initial data"),
        CATEGORY_NAME_REQUIRED("Name is required"),
        INVALID_OPERATION_INTENT("No permited operation."),
        USER_ALREADY_EXIST("Email already registered."),
        INVALID_TRANSACTION_INITIAL_DATA("Invalid transaction initial data");

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
