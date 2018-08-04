package bid.dbo.ftracker.accounts;

import lombok.Data;

@Data
public class CreateAccountCommand {
    private String userAccount;
    private String name;
    private String description;
}
