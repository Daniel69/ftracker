package bid.dbo.ftracker.users;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Builder
@Getter
public class UserAccount {
    private final String id;
    private final Date creationDate;
    private final List<String> owners;
}
