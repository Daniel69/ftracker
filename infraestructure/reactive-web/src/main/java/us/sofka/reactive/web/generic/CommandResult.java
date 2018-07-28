package us.sofka.reactive.web.generic;

public class CommandResult {

    private final boolean success;
    private final String code;
    private final String businessMessage;

    public CommandResult(boolean success, String code, String businessMessage) {
        this.success = success;
        this.code = code;
        this.businessMessage = businessMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getBusinessMessage() {
        return businessMessage;
    }

    public String getCode() {
        return code;
    }
}
