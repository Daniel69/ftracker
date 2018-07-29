package bid.dbo.ftracker.accounts;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class Account {
    private final String id;
    private final String name;
    private final String userAccount;
    private final Date openDate;
    private final String description;
    private final boolean active;
}
