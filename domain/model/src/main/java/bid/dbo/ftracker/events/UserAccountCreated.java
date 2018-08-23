package bid.dbo.ftracker.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAccountCreated implements Event {

    public static final String NAME = "UserAccountCreated";
    private final String accountId;

    @Override
    public String getName() {
        return NAME;
    }
}
