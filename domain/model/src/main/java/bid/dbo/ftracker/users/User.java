package bid.dbo.ftracker.users;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class User {
    private final String email;
    private final String fullName;
    private final Date singUpDate;
    private final String mainAccount;
    private final String authorization;
}
